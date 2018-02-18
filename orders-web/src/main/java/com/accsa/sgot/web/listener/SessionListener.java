package com.accsa.sgot.web.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Este filtro se ejecuta en cada request. Tiene propositos para debugging y control de excepciones.
 */
public class SessionListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public void afterPhase(PhaseEvent event) {
	// JSFUtils.isLoggedOn();
    }

    /**
     * Devuelva la fase en la cual se debe ejecutar after y before
     */
    @Override
    public PhaseId getPhaseId() {
	return PhaseId.RENDER_RESPONSE;
    }
}
