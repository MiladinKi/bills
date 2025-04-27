package bills.entities.dtos;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class BillDTO {
    private Integer id;
    @NotBlank(message = "Name must be provided!")
    private String name;
    @Max(value = 300, message = "Maximum characters in description is 300!")
    private String description;
    @NotNull(message = "Interval is mandatory.")
    @Min(value = 1, message = "The minimum interval must be 1.")
    @Max(value = 12, message = "The maximum interval can be bigger then 12!")
    private Integer interval;

    List<Integer> paymentIds = new ArrayList<>();

    List<Integer> totalPaymentIds = new ArrayList<>();

    public BillDTO() {
    }

    public BillDTO(Integer id, String name, String description, Integer interval, List<Integer> paymentIds, List<Integer> totalPaymentIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.interval = interval;
        this.paymentIds = paymentIds;
        this.totalPaymentIds = totalPaymentIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "Name must be provided!") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name must be provided!") String name) {
        this.name = name;
    }

    public @Max(value = 300, message = "Maximum characters in description is 300!") String getDescription() {
        return description;
    }

    public void setDescription(@Max(value = 300, message = "Maximum characters in description is 300!") String description) {
        this.description = description;
    }

    public @NotNull(message = "Interval is mandatory.") @Min(value = 1, message = "The minimum interval must be 1.") @Max(value = 12, message = "The maximum interval can be bigger then 12!") Integer getInterval() {
        return interval;
    }

    public void setInterval(@NotNull(message = "Interval is mandatory.") @Min(value = 1, message = "The minimum interval must be 1.") @Max(value = 12, message = "The maximum interval can be bigger then 12!") Integer interval) {
        this.interval = interval;
    }

    public List<Integer> getPaymentIds() {
        return paymentIds;
    }

    public void setPaymentIds(List<Integer> paymentIds) {
        this.paymentIds = paymentIds;
    }

    public List<Integer> getTotalPaymentIds() {
        return totalPaymentIds;
    }

    public void setTotalPaymentIds(List<Integer> totalPaymentIds) {
        this.totalPaymentIds = totalPaymentIds;
    }
}
