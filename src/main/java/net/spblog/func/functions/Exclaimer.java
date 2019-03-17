package net.spblog.func.functions;

import java.util.function.Function;

import reactor.core.publisher.Flux;

public class Exclaimer implements Function<Flux<String>, Flux<String>> {

	@Override
	public Flux<String> apply(Flux<String> words) {
		return words.map(word -> word + "!!!");
	}

}