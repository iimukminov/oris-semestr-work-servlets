package ru.kpfu.itis.mukminov.dto;

import ru.kpfu.itis.mukminov.entity.Part;

public class PartQuantityDto {
    private Part part;
    private int quantity;

    public PartQuantityDto() {}

    public PartQuantityDto(Part part, int quantity) {
        this.part = part;
        this.quantity = quantity;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

