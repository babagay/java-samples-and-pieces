package JavaCore.Module06;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({Processor.ANNOTATION_TYPE})
public class Processor extends AbstractProcessor
{
    public static final String ANNOTATION_TYPE = "JavaCore.Module06.Foo";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {

        System.out.println("_______________");

        return false;
    }
}
