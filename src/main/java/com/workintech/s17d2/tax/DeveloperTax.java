package com.workintech.s17d2.tax;
import org.springframework.stereotype.Component;

@Component
public class DeveloperTax implements Taxable {

    public Double getSimpleTaxRate() {
        return 15d;
    }

    public Double getMiddleTaxRate() {
        return 25d;
    }

    public Double getUpperTaxRate() {
        return 35d;
    }
}