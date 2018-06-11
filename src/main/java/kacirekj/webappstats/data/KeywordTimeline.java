package kacirekj.webappstats.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor // it have to be here becouse JPA want default construcotr...
@Entity
public class KeywordTimeline {
    @Id @GeneratedValue
    Integer id;
    Long measuredTime;
    @ManyToOne
    Keyword keyword;
    Integer count;
    String specialColumn;
}
