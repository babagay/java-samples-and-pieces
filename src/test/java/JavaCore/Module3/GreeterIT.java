package JavaCore.Module3;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import org.testng.annotations.Test;

import java.io.File;

// Указываем Arquillian в качестве JUnit раннера
//@RunWith(Arquillian.class)
public class GreeterIT {
    /*
    Метод, помеченный аннотацией Deployment, формирует архив, который Arquillian будет деплоить
    в контейнер перед выполнением тестов.
    По умолчанию для архива создается обертка, позволяющая выполнить тесты в рамках контейнера.
    В нашем случае в этом необходимости нет, тесты должны запускаться на стороне клиента,
    поэтому выставляем testable в false.
    */
    @Deployment(testable = false)
    public static Archive<?> createDeployment() {
        /*
        ShrinkWrap позволяет филигранно создавать микродеплойменты, изолируя части приложения,
        которые мы хотим протестировать, и уменьшая время деплоя. Но, к сожалению, иногда
        (особенно в больших приложениях с кучей зависимостей) сложно выделить часть для микродеплоймента,
        в таком случае можно просто взять архив, заботливо собранный Maven-ом.
        */
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("target/ws-autotesting.war"));
    }

    @Test
    public void testGreeter() throws Exception {
        System.out.println("Greeter test");


    }
}
