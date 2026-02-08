package com.example.splitwise.split;

import com.example.splitwise.enums.SplitType;
import com.example.splitwise.exception.validation.InvalidSplitException;
import com.example.splitwise.split.impl.EqualSplitStrategy;
import com.example.splitwise.split.impl.ExactSplitStrategy;
import com.example.splitwise.split.impl.PercentageSplitStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class SplitStrategyFactory {
    private final Map<SplitType, SplitStrategy> strategyMap;

    public SplitStrategyFactory(
            EqualSplitStrategy equal,
            ExactSplitStrategy exact,
            PercentageSplitStrategy percentage) {
        strategyMap = Map.of(
                SplitType.EQUAL, equal,
                SplitType.EXACT, exact,
                SplitType.PERCENTAGE, percentage
        );
    }

    public SplitStrategy getStrategy(SplitType splitType) {
        SplitStrategy strategy = strategyMap.get(splitType);
        if(Objects.isNull(strategy)){
            throw InvalidSplitException.invalidSplitType(splitType);
        }
        return strategy;
    }
}
