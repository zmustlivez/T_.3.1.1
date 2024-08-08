package com.example.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }


    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }


    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        Filter[] filters;
        CharacterEncodingFilter encFilter;
        HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();
        encFilter = new CharacterEncodingFilter();
        encFilter.setEncoding("UTF-8");
        encFilter.setForceEncoding(true);
        filters = new Filter[]{httpMethodFilter, encFilter};
        return filters;
    }
}