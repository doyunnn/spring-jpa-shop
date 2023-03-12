package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    // setter를 사용해 변수를 바꾸는 것이 아닌 아래와 같은 형태로 작성 후 핵심 메소드를 통해 setter를 대신 사용
    /**
    * stock 증가
    */
    public void  addStock(int quantity){
        this.stockQuantity += quantity;
    }
    /**
     * stock 감소
     */
    public void  removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0){
            throw  new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
