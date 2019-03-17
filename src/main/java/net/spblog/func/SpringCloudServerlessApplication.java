package net.spblog.func;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.compiler.FunctionCompiler;
import org.springframework.cloud.function.compiler.proxy.LambdaCompilingFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class SpringCloudServerlessApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudServerlessApplication.class, args);
	}
	
	@Bean
	public Function<String, String> uppercase() {
		return value -> value.toUpperCase();
	}
	
	@Bean
	public Function<Flux<String>, Flux<String>> lowercase() {
		return flux -> flux.map(value -> value.toLowerCase());
	}
	
	@Bean
	public Supplier<String> hello() {
		return () -> "hello";
	}
	
	@Bean
	public Supplier<Flux<String>> words() {
		return () -> Flux.fromArray(new String[] {"foo", "bar"});
	}
	
	@Bean
	public Function<String, String> compiledUppercase(
			FunctionCompiler<String, String> compiler) {
		String lambda = "s -> s.toUpperCase()";
		LambdaCompilingFunction<String, String> function = new LambdaCompilingFunction<>(
				new ByteArrayResource(lambda.getBytes()), compiler);
		function.setTypeParameterizations("String", "String");
		return function;
	}
	
	@Bean
	public Function<Flux<String>, Flux<String>> compiledLowercase(
			FunctionCompiler<Flux<String>, Flux<String>> compiler) {
		String lambda = "f->f.map(o->o.toString().toLowerCase())";
		return new LambdaCompilingFunction<>(new ByteArrayResource(lambda.getBytes()),
				compiler);
	}
	
	@Bean
	public <T, R> FunctionCompiler<T, R> compiler() {
		return new FunctionCompiler<>();
	}
}
