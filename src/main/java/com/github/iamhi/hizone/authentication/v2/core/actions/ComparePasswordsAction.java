package com.github.iamhi.hizone.authentication.v2.core.actions;

import java.util.function.BiFunction;

@FunctionalInterface
public interface ComparePasswordsAction extends BiFunction<String, String, Boolean> {
}
