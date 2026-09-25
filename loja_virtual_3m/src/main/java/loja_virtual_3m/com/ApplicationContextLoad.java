package loja_virtual_3m.com;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextLoad implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {

		applicationContext = appContext;

	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;

	}
}
