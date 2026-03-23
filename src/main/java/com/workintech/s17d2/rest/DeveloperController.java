package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    public Map<Integer, Developer> developers;

    private final Taxable taxable;

    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }

    @GetMapping
    public List<Developer> getAll() {
        return new ArrayList<>(developers.values());
    }

    @GetMapping("/{id}")
    public Developer getById(@PathVariable Integer id) {
        return developers.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Developer add(@RequestBody Developer dev) {

        Developer developer = null;

        if (dev.getExperience() == Experience.JUNIOR) {
            double salary = dev.getSalary() -
                    (dev.getSalary() * taxable.getSimpleTaxRate() / 100);
            developer = new JuniorDeveloper(dev.getId(), dev.getName(), salary);
        }

        if (dev.getExperience() == Experience.MID) {
            double salary = dev.getSalary() -
                    (dev.getSalary() * taxable.getMiddleTaxRate() / 100);
            developer = new MidDeveloper(dev.getId(), dev.getName(), salary);
        }

        if (dev.getExperience() == Experience.SENIOR) {
            double salary = dev.getSalary() -
                    (dev.getSalary() * taxable.getUpperTaxRate() / 100);
            developer = new SeniorDeveloper(dev.getId(), dev.getName(), salary);
        }

        developers.put(developer.getId(), developer);
        return developer;
    }

    @PutMapping("/{id}")
    public Developer update(@PathVariable Integer id, @RequestBody Developer developer) {
        developers.put(id, developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    public Developer delete(@PathVariable Integer id) {
        return developers.remove(id);
    }
}