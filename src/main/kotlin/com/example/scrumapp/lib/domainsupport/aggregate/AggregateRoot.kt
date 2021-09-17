package com.example.scrumapp.lib.domainsupport.aggregate

import org.springframework.data.domain.AbstractAggregateRoot

abstract class AggregateRoot<T : AbstractAggregateRoot<T>> : AbstractAggregateRoot<T>() {
    public override fun domainEvents(): Collection<Any> {
        return super.domainEvents()
    }

    public override fun clearDomainEvents() {
        super.clearDomainEvents()
    }
}