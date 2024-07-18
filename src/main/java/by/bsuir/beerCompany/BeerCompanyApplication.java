package by.bsuir.beerCompany;

import by.bsuir.beerCompany.bean.SomeUselessBean;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class BeerCompanyApplication {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("bean-configuration.xml");
		SomeUselessBean someUselessBean = (SomeUselessBean) context.getBean("someUselessBean");
		System.out.println(someUselessBean.getSomeInt());
		System.out.println(someUselessBean.getSomeText());
		System.out.println(someUselessBean.isSomeBoolean());

		SpringApplication.run(BeerCompanyApplication.class, args);
	}
}
