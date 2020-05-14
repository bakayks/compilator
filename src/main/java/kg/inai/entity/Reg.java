package kg.inai.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reg")
public class Reg {
    @Id
    @SequenceGenerator(name = "reg_seq", sequenceName = "reg_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reg_seq")
    private Long id;


    @Column(name = "name")
    private String name;
}
