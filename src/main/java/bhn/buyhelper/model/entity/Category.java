package bhn.buyhelper.model.entity;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import bhn.buyhelper.view.CategoryView;
import bhn.buyhelper.view.ProductView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(generator = "catIdGen")
    @GenericGenerator(name = "catIdGen", parameters = @Parameter(name = "prefix", value = "CAT"), strategy = "bhn.buyhelper.utils.IdGenerator")
    @JsonView(value = { CategoryView.BaseView.class, ProductView.AllView.class })
    private String catId;

    @JsonView(value = { CategoryView.BaseView.class, ProductView.AllView.class })
    private String catName;

    @ManyToMany(mappedBy = "listCategories")
    private Set<Product> listProducts;

}