package com.example.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
@Component
public class CustomizedTokenTransformationFilter extends ZuulFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getPrincipal().toString();
        List<String> roles = authentication.getAuthorities().stream().map(auto -> ((GrantedAuthority) auto).getAuthority()).collect(Collectors.toList());
        HashMap<String, Object> userDetail = new HashMap<>();
        userDetail.put("userId", userId);
        userDetail.put("roles", roles);

        String userDetailString = null;
        try {
            userDetailString = objectMapper.writeValueAsString(userDetail);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RequestContext.getCurrentContext().addZuulRequestHeader("X-user-detail", userDetailString);

        return null;
    }
}
