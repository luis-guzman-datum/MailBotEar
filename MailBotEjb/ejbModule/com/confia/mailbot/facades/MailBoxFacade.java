/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.confia.mailbot.dto.MailBoxDto;
import com.confia.mailbot.exceptions.UpdateGestionException;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.MailTail;

/**
 *
 * @author lumelen
 */

@Remote(MailBoxFacadeRemote.class)
@Local(MailBoxFacadeLocal.class)
@Stateless(mappedName = "ejb/MailBoxFacade")
public class MailBoxFacade extends AbstractFacade<MailBox> implements MailBoxFacadeLocal {

	@PersistenceContext(unitName = "MailBotEap-ejbPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public MailBoxFacade() {
		super(MailBox.class);
	}

	public List<MailBox> obtenerPor(BigDecimal pTail, String pEstado, Integer pCantidad) {
		List<MailBox> lista = null;

		// si se restringe la cantidad de correos
		if (pCantidad.intValue() > 0) {

			Query qParcial = getEntityManager().createQuery("select mb " + "from MailBox mb "
					+ "where mb.idTail.idTail = :pTail " + "and mb.estado = :pEstado ");

			qParcial.setParameter("pTail", pTail);
			qParcial.setParameter("pEstado", pEstado);
			qParcial.setFirstResult(0);
			qParcial.setMaxResults(pCantidad);

			lista = qParcial.getResultList();

			// si no esta restringida la cantidad
		} else {

			Query qTotal = getEntityManager().createQuery("select mb " + "from MailBox mb "
					+ "where mb.idTail.idTail = :pTail " + "and mb.estado = :pEstado ");

			qTotal.setParameter("pTail", pTail);
			qTotal.setParameter("pEstado", pEstado);

			lista = qTotal.getResultList();
		}

		return lista;
	}

	public List<MailBox> obtenerMailBoxPorGestion(String paramGestion, String paramEstado) {

		String queryString = "select mb from MailBox mb " + " where mb.actualizaGestion = :pGestion "
				+ " and mb.estado = :pEstado " + " and (:currentDate - mb.fechaEnvio)  > 0.25 ";

		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("pGestion", paramGestion);
		query.setParameter("pEstado", paramEstado);
		query.setParameter("currentDate", new Date());

		return query.getResultList();

	}

	public List<Object[]> obtenerMailBoxPorGestionNative() {

		String queryString = "select id_mail, referencia from mail_box " + " where actualiza_gestion = 'S' "
				+ " and estado = 'E' " + " and (sysdate - fecha_envio)  > 0.25 ";

		Query query = getEntityManager().createNativeQuery(queryString);
		return query.getResultList();

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int updateGestion(String estado, String periodo, int numeroGestion) throws UpdateGestionException{
		try {
			String queryString = "update ge_gestion " + " set estado_gestion = ?1 ," + " {SET_FECHA_GESTION} "
					+ " where cod_empresa = '1' " + " and periodo = ?2 " + " and numero_gestion = ?3 ";
	
		
			if (estado.equals("RES")) {
				queryString = queryString.replace("{SET_FECHA_GESTION}", " fecha_resuelta = trunc(sysdate) ");
			} else {
				queryString = queryString.replace("{SET_FECHA_GESTION}", " fecha_cerrada = trunc(sysdate) ");
			}
			
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter(1, estado);
			query.setParameter(2, periodo);
			query.setParameter(3, numeroGestion);
			int updatedRows=query.executeUpdate();
			return updatedRows;
		}catch(Exception ex) {
			throw new UpdateGestionException("ACTUALIZAR GESTION: 0 rows updated - Exception: "+ ex.getMessage());

		}
		
	}

	@Override
	public List<MailBox> findByDatesAndStatus(Date initDate, Date endDate, List<String> statusList) {
		String queryString = "Select T From MailBox T where T.fechaAdicion >= :initDate and T.fechaAdicion < :endDate and T.estado in (:statusList)";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("initDate", initDate);
		query.setParameter("endDate", endDate);
		query.setParameter("statusList", statusList);
		return query.getResultList();
	}

	@Override
	public List<MailBox> findByDatesAndStatusOpt(Date initDate, Date endDate, List<String> statusList) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<MailBox> q = cb.createQuery(MailBox.class);

		Root<MailBox> mb = q.from(MailBox.class);
		CriteriaQuery<MailBox> select = q.select(mb);

		ParameterExpression<Date> inDate = cb.parameter(Date.class, "initDate");
		ParameterExpression<Date> endingDate = cb.parameter(Date.class, "endDate");
		ParameterExpression<String> status = cb.parameter(String.class, "estado");
		Expression<Date> dateCreated = mb.<Date>get("fechaAdicion");
		Expression<String> statusPath = mb.<String>get("estado");

		Predicate p1 = cb.greaterThanOrEqualTo(dateCreated, inDate);
		Predicate p2 = cb.lessThan(dateCreated, endingDate);
		Predicate p3 = statusPath.in(statusList);

		select.where(cb.and(p1, p2, p3));
		TypedQuery<MailBox> typedQuery = getEntityManager().createQuery(select);
		typedQuery.setParameter("initDate", initDate);
		typedQuery.setParameter("endDate", endDate);

		return typedQuery.getResultList();
	}

	@Override
	public List<MailBoxDto> findByDatesAndStatusOptNQ(Date initDate, Date endDate, List<String> statusList) {
		String queryString = "Select T.ID_MAIL, T.ID_PROCESO, T.ID_TAIL, T.ESTADO, T.FECHA_ADICION, T.msg From MAIL_BOX T "
				+ "where T.FECHA_ADICION >= ?1 and T.FECHA_ADICION < ?2 "
				+ "and (T.ESTADO = ?3 or T.ESTADO = ?4)";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter(1, initDate);
		query.setParameter(2, endDate);
		query.setParameter(3, "X");
		query.setParameter(4, "R");
		List<MailBoxDto> dtoList = new ArrayList<MailBoxDto>();
		List<Object[]> queryResult = query.getResultList();
		int count = 0;
		String dateString = "";
		for(Object[] obj: queryResult) {
			
			String idTail = "";
			String idProceso = "";
			String idMail = "";
			if(obj[0]!=null) {
				BigDecimal bd = (BigDecimal)obj[0];
				idMail = bd.toString();
			}	
			
			if(obj[1]!=null) {
				BigDecimal bd = (BigDecimal)obj[1];
				idTail = bd.toString();
			}			
			if(obj[2]!=null) {
				BigDecimal bd = (BigDecimal)obj[2];
				idProceso = bd.toString();
			}
			
			if(obj[4]!=null) {
				Date dateAdded = (Date)obj[4];
				dateString = format.format(dateAdded);
			}
			MailBoxDto dto = new MailBoxDto(idMail,idTail,idProceso,(String)obj[3],dateString, (String)obj[5]);
			dto.setCount(String.valueOf(count++));
			dtoList.add(dto);
		} 	
		//System.out.println("TAM REGISTROS: "+dtoList.size());

		return dtoList;
	}

}
