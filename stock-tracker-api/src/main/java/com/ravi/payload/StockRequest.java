package com.ravi.payload;

import javax.validation.constraints.NotBlank;



public class StockRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String operation;

    @NotBlank
    private String price;

    @NotBlank
    private String percent;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the percent
     */
    public String getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(String percent) {
        this.percent = percent;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

   }
