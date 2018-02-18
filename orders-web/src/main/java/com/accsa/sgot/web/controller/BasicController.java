package com.accsa.sgot.web.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.collections4.MapUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.sun.faces.mgbean.ManagedBeanInfo.ManagedProperty;

public abstract class BasicController implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * Flash Scope para agregar y obtener atributos en una redirección.
	 */
	protected Flash flash = Faces.getExternalContext()
			.getFlash();

	protected RequestContext requestContext = RequestContext.getCurrentInstance();

	protected Map<String, String> requestParameterMap = Faces.getRequestParameterMap();

	/**
	 * Estado de la edición de una entidad
	 *
	 * @author ahernandez
	 */
	protected enum State {
		Query(false),
		Edit(true);

		private boolean isEditing;

		private State(boolean edit) {
			isEditing = edit;
		}
	}

	public boolean isEditing() {
		return state.isEditing;
	}

	public void handleChangeEdit() {
		if (state == State.Edit) {
			growlToUser("Puede editar los datos");
		} else {
			growlToUser("Los datos ahora no son editables");
		}
	}

	/**
	 * Setea el atributo {@link #state} del controlador en {@link State#Edit} or {@link State#Query} La vista puede
	 * habilitar o deshabilitar la edición de una entidad basandose en este atributo.
	 * Atención: Los métodos se nombran con la conveción JavaBean para que puedan ser accedidos desde las vistas.
	 *
	 * @param editing
	 */
	public void setEditing(boolean editing) {
		state = editing ? State.Edit : State.Query;
	}

	/**
	 * Envia un mensaje al usuario como un GROWL. Su uso esta orientado a ser llamado desde la vista
	 */
	public void growlToUser(String msg) {
		info(msg, "todo-growl");
	}

	/**
	 * Envia un mensaje al usuario como un HEADER.
	 * Observación: No puede ser llamado desde un PostConstruct ya que el componente p:messages esta en un template y se
	 * renderiza luego del PC
	 */
	public void msgToUser(String msg) {
		info(msg, "todo-target");
	}

	protected State state = State.Query;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Logger SLF4J instanciado por cada controlador
	 */
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Navigation Handler. Este método sirve para navegar a traves de los
	 * outcome definidos en faces-config.xml o implícitamente.
	 */
	private ConfigurableNavigationHandler navigationHandler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance()
			.getApplication()
			.getNavigationHandler();

	/**
	 * Devuelve una URL que contiene el outcome, los parámetros y si hay redirección o no. Internamente utiliza un
	 * {@link StringBuilder}.
	 *
	 * @param size
	 * @param outcome
	 * @param params
	 * @param redirect
	 * @return
	 */
	private String navURL(String outcome, String params, boolean redirect) {
		StringBuilder pattern = new StringBuilder(outcome.length() + params.length() + String.valueOf(redirect)
				.length());
		pattern.append(outcome)
				.append("?")
				.append(params)
				.append("faces-redirect=")
				.append(redirect);

		return pattern.toString();
	}

	/**
	 * Devuelve una URL que contiene el outcome, los parámetros. Internamente utiliza un {@link StringBuilder}.
	 *
	 * @param size
	 * @param outcome
	 * @param redirect
	 * @return
	 */
	private String navURL(String outcome, String params) {
		StringBuilder pattern = new StringBuilder(outcome.length() + params.length());
		pattern.append(outcome);
		if (params.length() > 0) {
			pattern.append("?")
					.append(params);
		}
		return pattern.toString();
	}

	/**
	 * Versión sobreescrita de {@link ConfigurableNavigationHandler#performNavigation(String)}. Se espera que
	 * la bandera faces-redirect esté declarada en el <navigation-case/> del navigation-config.xml
	 *
	 * @param outcome
	 */
	protected void performNavigation(String outcome) {
		navigationHandler.performNavigation(navURL(outcome, ""));
	}

	/**
	 * Versión sobreescrita de {@link ConfigurableNavigationHandler#performNavigation(String)}. Agrega
	 * la bandera faces-redirect=[true|false] al final de la URL
	 *
	 * @param outcome
	 * @param redirect
	 */
	protected void performNavigation(String outcome, boolean redirect) {
		navigationHandler.performNavigation(navURL(outcome, "", redirect));
	}

	/**
	 * Versión sobreescrita de {@link ConfigurableNavigationHandler#performNavigation(String)}. Agrega
	 * la bandera faces-redirect=[true|false] al final de la URL y keepMessage del ScopeFlash para mantener los mensajes
	 * en la navegación
	 *
	 * @param outcome
	 * @param redirect
	 */
	protected void performNavigation(String outcome, boolean redirect, boolean keepMessage) {
		flash.setKeepMessages(keepMessage);
		navigationHandler.performNavigation(navURL(outcome, "", redirect));
	}

	/**
	 * Agrega parámetros a la URL. Ver {@link ConfigurableNavigationHandler#performNavigation(String, boolean)}
	 *
	 * <pre>
	 * Ej:
	 *
	 * {@code
	 * performNavigation(OutComes.DetalleUsuario, true, "id="+usuarioDTO.getId(), "Funciona=SI!!!");}
	 * </pre>
	 *
	 * @param outcome
	 * @param redirect
	 * @param paramsArray
	 */
	protected void performNavigation(String outcome, boolean redirect, String... paramsArray) {

		StringBuilder params = new StringBuilder(64);

		if (paramsArray.length > 0) {
			List<String> paramsList = Arrays.asList(paramsArray);

			paramsList.forEach((param -> {
				params.append(param)
						.append("&");
			}));
		}

		navigationHandler.performNavigation(navURL(outcome, params.toString(), redirect));
	}

	/**
	 * Agrega parámetros a la URL. Ver {@link ConfigurableNavigationHandler#performNavigation(String, boolean)}
	 *
	 * <pre>
	 * Ej:
	 * {@code
	 *   Map<String, String> map = new HashMap<>();}
	 *   map.put("id", String.valueOf(usuarioDTO.getId()));
	 *   map.put("Funciona", "SI!!!");
	 *   performNavigation(OutComes.DetalleUsuario, true, map);}
	 * </pre>
	 *
	 * @param outcome
	 * @param redirect
	 * @param paramsMap
	 */
	protected void performNavigation(String outcome, boolean redirect, Map<String, String> paramsMap) {
		StringBuilder params = new StringBuilder(128);

		if (MapUtils.isNotEmpty(paramsMap)) {
			paramsMap.forEach((k, v) -> {
				params.append(k)
						.append("=")
						.append(v)
						.append("&");
			});
		}

		navigationHandler.performNavigation(navURL(outcome, params.toString(), redirect));
	}

	/**
	 * Este método devuelve un bean del contenedor, dado el tipo que lo
	 * representa. Su inclusión es debido a que para traer beans de Spring en un {@link ManagedBean} se podria utilizar
	 * {@link ManagedProperty} y EL. De
	 * este modo, precindiendo de este metodo se podria inyectar un bean así:
	 * </br>
	 * <code>	@ManagedProperty("#{usuarioRPC}")</code>
	 * </p>
	 * Con este método
	 * se escribe menos código y no hay que preocuparse de declarar con un
	 * String el nombre del bean de Spring.
	 * </p>
	 *
	 * @param beanType
	 *            - El tipo del bean que se desea obtener del contenedor
	 * @return El bean Spring de tipo beanType
	 */
	protected <T> T getSpringBean(Class<T> beanType) {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		return ctx.getBean(beanType);
	}

	/**
	 * Este método envía un mensaje al usuario y loguea una {@link Exception}.
	 * Puede ser llamado desde un catch. Imprime la excepción al logger si es !=
	 * de null y envía el mensaje correspondiente a la key al usuario. Si la
	 * excepción e es null entonces solo envia el mensaje al usuario.
	 *
	 * @param key
	 *            La clave de messages.properties. Si no encuentra la clave
	 *            devuelve la misma.
	 * @param e
	 *            La excepción a ser lanzada. Si e == null entonces no se loguea
	 *            el error a la consola.
	 */
	protected void warn(String key, Exception e) {
		log.warn(Messages.create(key)
				.get()
				.getDetail(), e);
		Messages.addGlobalWarn(key);
	}

	/**
	 * Este método envía un mensaje warn al usuario.
	 *
	 * @param key
	 *            La clave de messages.properties. Si no encuentra la clave
	 *            devuelve la misma.
	 */
	protected void warn(String key) {
		Messages.addGlobalWarn(key);
	}

	/**
	 * Este método envía un mensaje al usuario y loguea una {@link Exception}.
	 * Puede ser llamado desde un catch. Imprime la excepción al logger si es !=
	 * de null y envía el mensaje correspondiente a la key al usuario. Si la
	 * excepción e es null entonces solo envia el mensaje al usuario.
	 *
	 * @param key
	 *            La clave de messages.properties. Si no encuentra la clave
	 *            devuelve la misma.
	 * @param e
	 *            La excepción a ser lanzada. Si e == null entonces no se loguea
	 *            el error a la consola.
	 */
	protected void error(String key, Exception e) {
		key = Objects.isNull(key) ? "" : key; // si es NPE o el message es null tira una NPE
		log.error(Messages.create(key)
				.get()
				.getDetail(), e);
		Messages.addGlobalError(key);
	}

	/**
	 * Agrega un clientId a {@link #error(String, Exception)}.
	 * AVISO: No se puede llamar este método desde PostConstruct hacia un clientId fuera de un panel desde donde se
	 * llama. Por ejemplo en el header.
	 * Si se puede desde un actionListener o action en un submit.
	 *
	 * @param key
	 * @param clientId
	 * @param e
	 */
	protected void error(String key, String clientId, Exception e) {
		log.error(Messages.create(key)
				.get()
				.getDetail(), e);
		Messages.addError(clientId, key);
	}

	/**
	 * Error para ser enviado al usuario, toma la el mensaje correspondiente a
	 * la key, de no encontrar la key devuelve la misma.
	 *
	 * @param key
	 */
	protected void error(String key) {
		Messages.addGlobalError(key);
	}

	/**
	 * Agrega un clientId a {@link #error(String)}.
	 *
	 * @param key
	 * @param clientId
	 */
	protected void error(String key, String clientId) {
		Messages.addError(clientId, key);
	}

	/**
	 * Info para se llamado desde los controladores. Envía un mensaje al usuario
	 * hacia un tag p:messages global proporcionado al usuario.
	 *
	 * @param key
	 *            La clave de messages.properties
	 */
	protected void info(String key) {
		Messages.addGlobalInfo(key);
	}

	/**
	 * Agrega un clientId a {@link #info(String)}.
	 * AVISO: No se puede llamar este método desde PostConstruct hacia un clientId fuera de un panel desde donde se
	 * llama. Por ejemplo en el header.
	 *
	 * @param key
	 * @param clientId
	 */
	protected void info(String key, String clientId) {
		Messages.addInfo(clientId, key);
	}

	/**
	 * Método sobrecargado para soportar números. Internamente llama a {@link #info(String)}
	 *
	 * @param number
	 */
	protected void info(Number number) {
		String text = String.valueOf(number);
		Messages.addGlobalInfo(text);
	}

	/**
	 * Info para se llamado desde los controladores. Envía un mensaje al usuario
	 * hacia un tag p:messages global proporcionado al usuario. Usar éste método
	 * si es necesario pasar parámetros a la key.
	 */
	protected void infoWithParameters(String key, Object... params) {
		Messages.addGlobalInfo(key, params);
	}

	/**
	 * Error para se llamado desde los controladores. Envía un mensaje al usuario
	 * hacia un tag p:messages global proporcionado al usuario. Usar éste método
	 * si es necesario pasar parámetros a la key.
	 */
	protected void errorWithParameters(String key, Object... params) {
		Messages.addGlobalError(key, params);
	}

	/**
	 * Marca un campo en rojo como no válido, dada su ruta de ID.<br>
	 * Ejemplo de uso: <b><code>markFieldAsInvalid(":form:tabs:celular_declarante");</code></b>
	 */
	protected void markFieldAsInvalid(String component) {
		UIInput input = (UIInput) Faces.getViewRoot()
				.findComponent(component);
		if (input != null) {
			input.setValid(false);
		}
	}

	/**
	 * Método utilitario para hacer reset de valores en ui:repeat.
	 **/
	public void validateData(ComponentSystemEvent event) {
		final FacesContext context = FacesContext.getCurrentInstance();
		event.getComponent()
				.visitTree(VisitContext.createVisitContext(context), (context1, target) -> {
					if (target instanceof UIInput) {
						if (!((UIInput) target).isValid()) {
							((UIInput) target).setValue(null);
							log.info("Component invalid: " + ((UIInput) target).getClientId());
						}
					}
					return VisitResult.ACCEPT;
				});
	}
}