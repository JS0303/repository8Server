package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping( value="json/getProduct/{prodNo}", method=RequestMethod.GET)
	public Product getProduct( @PathVariable int prodNo) throws Exception{
		
		System.out.println("/product/json/getProduct : GET");
		System.out.println(prodNo);
		//Business Logic
		return productService.getProduct(prodNo);
		
	}
	
	//@RequestMapping("/updateProductView.do")
	@RequestMapping( value="json/updateProduct/{prodNo}", method=RequestMethod.GET)
	public Product updateProduct(@PathVariable int prodNo, Model model) throws Exception {

		System.out.println("/product/updateProduct : GET");

		Product product = productService.getProduct(prodNo);

		model.addAttribute("product", product);

		return product;
	}

	//@RequestMapping("/updateProduct.do")
	@RequestMapping( value="json/updateProduct/{prodNo}", method=RequestMethod.POST)
	public Product updateProduct(@ModelAttribute("product") Product product, Model model)
			throws Exception {

		System.out.println("/product/updateProduct : POST");

		productService.updateProduct(product);

		model.addAttribute("product", product);
		
		return product;
	}
	
	@RequestMapping( value="/json/listProduct", method=RequestMethod.POST )
	public Map listProduct(@ModelAttribute("search") Search search, Model model, HttpServletRequest request)
			throws Exception {

		System.out.println("/product/listProduct : GET / POST");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		Map<String, Object> map = productService.getProductList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println(resultPage);

		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return map;
	}
	
	@RequestMapping( value="/json/addProduct/{prodNo}", method=RequestMethod.POST )
	public Product addProduct( @RequestBody Product product, Search search) throws Exception {

		System.out.println("/user/addUser : POST");
		//Business Logic
		
		productService.insertProduct(product);
		
		return product;
	}
}
