package uz.pdp.g34jpahibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    /*@SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            schema = "public",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")*/
    /*@TableGenerator(
            name = "user_seq_table",
            table = "user_seq_table",
            schema = "public",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_sequence")*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*@UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue(strategy = GenerationType.UUID)*/
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 16)
    private String password;

    private int age;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default now()" , updatable = false, nullable = false)
    private LocalDateTime creationTime;
}

