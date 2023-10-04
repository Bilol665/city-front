package uz.pdp.cityfront.domain.dto.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Filter {
    {
        if (this.perPage == 0) this.perPage = 10;
        if (this.page == 0) this.page = 1;
    }
    private Date endDate;
    private Date startDate;
    private double minPrice;
    private double maxPrice;
    private String type;
    private String status;
    private int page;
    private int perPage;
    private int numberOfFlats;
    private int floor;
    public void set(String name, String value) {
        switch (name) {
            case "numberOfFlats" -> this.numberOfFlats = Integer.parseInt(value);
            case "floor" -> this.floor = Integer.parseInt(value);
            case "endDate" -> {
                this.endDate = Date.from(Instant.parse(value));
            }
            case "startDate" -> {
                this.startDate = Date.from(Instant.parse(value));
            }
            case "minPrice" -> {
                this.minPrice = Double.parseDouble(value);
            }
            case "maxPrice" -> {
                this.maxPrice = Double.parseDouble(value);
            }
            case "type" -> this.type = value;
            case "status" -> this.status = value;
        }
    }

}
