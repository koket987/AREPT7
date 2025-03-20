package co.edu.eci.arep.AREPT7;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import co.edu.eci.arep.AREPT7.TwitterCloneApplication;

public class AuthLambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    static {
        try {
            // Inicializa el contenedor pasando la clase principal de tu aplicación Spring Boot
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(TwitterCloneApplication.class);
        } catch (ContainerInitializationException e) {
            throw new RuntimeException("No se pudo iniciar el contenedor Spring Boot", e);
        }
    }

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        return handler.proxy(awsProxyRequest, context);
    }
}
