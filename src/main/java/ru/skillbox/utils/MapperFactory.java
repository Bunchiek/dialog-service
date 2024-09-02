package ru.skillbox.utils;

public interface MapperFactory <S, T> {
    T map(S source);
}
