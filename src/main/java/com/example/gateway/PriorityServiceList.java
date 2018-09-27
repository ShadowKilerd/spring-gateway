package com.example.gateway;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class PriorityServiceList implements IRule {

    private ILoadBalancer lb;

    @Override
    public Server choose(Object key) {
        List<Server> allServers = this.lb.getReachableServers();

        int randomInt = (int) (Math.random() * allServers.size());
        System.out.println(randomInt);
        return allServers.get(randomInt);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.lb;
    }
}
