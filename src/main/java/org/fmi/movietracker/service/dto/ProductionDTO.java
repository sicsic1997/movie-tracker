package org.fmi.movietracker.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Production entity.
 */
public class ProductionDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductionDTO productionDTO = (ProductionDTO) o;
        if (productionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductionDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            "}";
    }
}
