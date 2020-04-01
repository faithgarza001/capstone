package com.capstone.kitsune.converters;

import com.capstone.kitsune.models.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements Converter<String, Category> {
    @Override
    public Category convert(String id) {

        return null;
    }
}
