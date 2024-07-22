package com.example.demo;

import com.example.demo.constants.UserRole;
import com.example.demo.dto.AuthenticationRequestDto;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.model.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

	@Bean
	CommandLineRunner init(AuthService userService, ProductService productService, CategoryService categoryService, UserDetailsService userDetailsService, WishlistService wishlistService){
		return args -> {


		/*	User user1 = new User();
			user1.setEmail("user1@gmail.com");
			user1.setName("user1");
			user1.setRole(UserRole.CUSTOMER);
			user1.setPassword(new BCryptPasswordEncoder().encode("user1"));

			User user2 = new User();
			user2.setEmail("user1@gmail.com");
			user2.setName("user2");
			user2.setRole(UserRole.CUSTOMER);
			user2.setPassword(new BCryptPasswordEncoder().encode("user2"));

			userService.save(user1);
			userService.save(user2);

			CategoryDto category1 = new CategoryDto();
			CategoryDto category2 = new CategoryDto();
			CategoryDto category3 = new CategoryDto();
			CategoryDto category4 = new CategoryDto();

			category1.setName("Sports");
			category2.setName("Winter");
			category3.setName("Summer");
			category4.setName("Outdoor");

			category1.setDescription("This category is for Sports clothing.");
			category2.setDescription("This category is for Winter clothing.");
			category3.setDescription("This category is for Summer clothing.");
			category4.setDescription("This category is for Outdoor clothing.");

			categoryService.createCategory(category1);
			categoryService.createCategory(category2);
			categoryService.createCategory(category3);
			categoryService.createCategory(category4);

			ProductDto product1 = new ProductDto();
			ProductDto product2 = new ProductDto();
			ProductDto product3 = new ProductDto();
			ProductDto product4 = new ProductDto();
			ProductDto product5 = new ProductDto();
			ProductDto product6 = new ProductDto();

			product1.setName("Shirt");
			product2.setName("Hoodie");
			product3.setName("Beanie");
			product4.setName("Pants");
			product5.setName("Shoes");
			product6.setName("Socks");

			product1.setPrice(20.1);
			product2.setPrice(40.2);
			product3.setPrice(12.2);
			product4.setPrice(30.3);
			product5.setPrice(100.4);
			product6.setPrice(5.2);

			product1.setDescription("This Shirts are perfect for Summer.");
			product2.setDescription("This hoodie is so cozy in the Winter season.");
			product3.setDescription("This beanie looks nice when going for a walk.");
			product4.setDescription("These are some good Pants for doing Sports in the Summer");
			product5.setDescription("These shoes are good for the Winter, cozy.");
			product6.setDescription("These are some universal socks made for all seasons");

			product1.setCategoryId(3L);
			product2.setCategoryId(2L);
			product3.setCategoryId(4L);
			product4.setCategoryId(1L);
			product5.setCategoryId(2L);
			product6.setCategoryId(3L);

			product1.setByteImg(null);
			product2.setByteImg(null);
			product3.setByteImg(null);
			product4.setByteImg(null);
			product5.setByteImg(null);
			product6.setByteImg(null);

			productService.addProduct(product1);
			productService.addProduct(product2);
			productService.addProduct(product3);
			productService.addProduct(product4);
			productService.addProduct(product5);
			productService.addProduct(product6);
*/
			/*List<ProductDto> productDtoList = productService.getAllProducts();
			System.out.println(productDtoList);

			ProductDto findProduct = productService.getProductById(1L);
			System.out.println(findProduct);

			boolean isDeletedProduct = productService.deleteProduct(1L);
			System.out.println(isDeletedProduct);*/

			//System.out.println(productService.getAllProducts());

			/*User resultUser = userService.findUserByName("admin");
			System.out.println(resultUser.getId());

			categoryService.deleteCategory(1L);*/

			//UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
			//System.out.println(userDetailsService.loadUserByUsername("asd"));
			/*User user12 = userService.findByEmail("admin@gmail.com");
			System.out.println(user12);
			System.out.println("\n");
			UserDetails userDetails = userDetailsService.loadUserByUsername("admin@gmail.com");
			System.out.println(userDetails);

			System.out.println(categoryService.getAllCategories());*/


			/*AuthenticationRequestDto authenticationRequest = new AuthenticationRequestDto();
			authenticationRequest.setEmail("admin@gmail.com");
			authenticationRequest.setPassword("admin");*/

			//authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("admin@gmail.com","admin" ));

			//System.out.println(productService.getAllProductByName("asdsddsdfsf"));

		};
	}

}
