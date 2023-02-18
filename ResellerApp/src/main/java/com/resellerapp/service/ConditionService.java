package com.resellerapp.service;

import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.enums.ConditionType;
import com.resellerapp.repository.ConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class ConditionService {
    private final ConditionRepository conditionRepository;

    @Autowired
    public ConditionService(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @PostConstruct
    private void postConstruct() {
        if (this.conditionRepository.count() == 0) {
            this.conditionRepository.saveAllAndFlush(Arrays.stream(ConditionType.values())
                    .map(Condition::new)
                    .collect(Collectors.toList()));
        }
    }
}
