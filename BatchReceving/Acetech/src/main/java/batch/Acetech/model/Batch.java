package batch.Acetech.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonAutoDetect
public class Batch {

    @Id
    @Column(name = "batchid", updatable = false, nullable = false)
    private String batchid;
    private String batchTypeId;
    private String batchTypeDescription;
    private LocalDate batchExpirationDate;
    private int batchCount;

    @Override
    public String toString() {
        return "Batch{" +
                "batchid='" + batchid + '\'' +
                ", batchTypeId='" + batchTypeId + '\'' +
                ", batchTypeDescription='" + batchTypeDescription + '\'' +
                ", batchExpirationDate='" + batchExpirationDate + '\'' +
                ", batchCount='" + batchCount + '\'' +
                '}';
    }
}
