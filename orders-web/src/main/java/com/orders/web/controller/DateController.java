package com.orders.web.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Created by Luis Costela on 22/3/2017.
 */

@ManagedBean(name = "dateController")
@ApplicationScoped
public class DateController extends BasicController implements Serializable {

  private static final long serialVersionUID = -8838402551131298736L;

  public String formadoFecha(String formato, Date fecha) {
    return (fecha == null ? "" : new SimpleDateFormat(formato).format(fecha));
  }

  public String formatoFecha(String formato, Calendar fecha) {
    return (fecha == null ? "" : new SimpleDateFormat(formato).format(fecha.getTime()));
  }
}
