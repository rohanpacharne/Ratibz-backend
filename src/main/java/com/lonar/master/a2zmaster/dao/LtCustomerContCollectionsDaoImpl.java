package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtCustomerContCollections;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtCustomerContCollections;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.repository.LtCustomerContCollectionsRepository;

@Repository
@PropertySource(value = "classpath:queries/contCollection.properties", ignoreResourceNotFound = true)
public class LtCustomerContCollectionsDaoImpl implements LtCustomerContCollectionsDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
	private LtCustomerContCollectionsRepository ltCustomerContCollectionsRepository;
	
	@Override
	public LtCustomerContCollections save(LtCustomerContCollections ltCustomerContCollections) throws ServiceException {
		return ltCustomerContCollectionsRepository.save(ltCustomerContCollections);
	}

	@Override
	public LtCustomerContCollections getLtCustomerContCollectionsById(Long conCollId) throws ServiceException {
		String query = env.getProperty("getLtCustomerContCollectionsById");
		//String query = " SELECT * FROM LT_CUSTOMER_CONT_COLLECTIONS WHERE CONT_COLLECTION_ID = ? ";
		LtCustomerContCollections ltCustomerContCollections=   jdbcTemplate.queryForObject(query, new Object[]{ conCollId}, 
				 new BeanPropertyRowMapper<LtCustomerContCollections>(LtCustomerContCollections.class)); 
		return ltCustomerContCollections;
	}

	@Override
	public LtCustomerContCollections delete(Long conCollId) throws ServiceException {
		ltCustomerContCollectionsRepository.deleteById(conCollId);
		if(ltCustomerContCollectionsRepository.existsById(conCollId)) {
			return ltCustomerContCollectionsRepository.findById(conCollId).get();
		}else
		return null;
	}

	@Override
	public List<LtCustomerSubsDeliveries> collectionSummary(LtCustomerContCollections ltCustomerContCollections)
			throws ServiceException {
		String query = env.getProperty("collectionSummary");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) 
				jdbcTemplate.query(query , new Object[]{ltCustomerContCollections.getSupplierId()},
			 new  BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}

	@Override
	public Long getDataTableCount(LtCustomerContCollections ltCustomerContCollections, Long supplierId)
			throws ServiceException {
		String query = env.getProperty("getontCollectionsDataTableCount");

		String name = null;
		if (ltCustomerContCollections.getSearchParameter() != null && !ltCustomerContCollections.getSearchParameter().equals("")) {
			name = "%" + ltCustomerContCollections.getSearchParameter().trim().toUpperCase() + "%";
		}
		return  getJdbcTemplate().queryForObject(query, new Object[] { supplierId, name },
				Long.class);
	}

	@Override
	public List<LtCustomerSubsDeliveries> getDataTable(LtCustomerContCollections ltCustomerContCollections,
			Long supplierId , String isOwnContainer ) throws ServiceException {
		if (ltCustomerContCollections.getSort() == null) {
			ltCustomerContCollections.setSort("desc");
		}
		if (ltCustomerContCollections.getColumnNo() == null || ltCustomerContCollections.getColumnNo() == 0) {
			ltCustomerContCollections.setColumnNo(1);
		}

		String name = null;
		if (ltCustomerContCollections.getSearchParameter() != null && !ltCustomerContCollections.getSearchParameter().equals("")) {
			name = "%" + ltCustomerContCollections.getSearchParameter().trim().toUpperCase() + "%";
		}
		String query  = ""; 
		
		if( isOwnContainer != null &&  isOwnContainer.equalsIgnoreCase("Y") ) {
			query = env.getProperty("getContCollectionsDataTable");	
		}else {
			query = env.getProperty("getContDeliveredDataTable");
		}

		return (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query, new Object[] { supplierId, name, 
				ltCustomerContCollections.getLength() , ltCustomerContCollections.getStart() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
	}

	@Override
	public Long getConCollectedByUserProductSubsDeliveryDate(LtCustomerSubsDeliveries customerSubsDeliveries)
			throws ServiceException {
		String query = env.getProperty("getConCollectedByUserProductSubsDeliveryDate");
		List<LtCustomerContCollections> list = (List<LtCustomerContCollections>) 
				jdbcTemplate.query(query , new Object[]{customerSubsDeliveries.getUserId(),customerSubsDeliveries.getProductId(),
						customerSubsDeliveries.getSubsId(),customerSubsDeliveries.getDeliveryDate()},
			 new  BeanPropertyRowMapper<LtCustomerContCollections>(LtCustomerContCollections.class));
		if(!list.isEmpty()) {
			return list.get(0).getCollectedQuantity();
		}else
		return null;
	}

	@Override
	public Double getContainerDeliveredQuantity(Long subId) throws Exception {
		String query = "select sum(delivery_quantity) from lt_customer_subs_deliveries where subs_id = ? ";
		return jdbcTemplate.queryForObject(query, new Object[] {subId }, Double.class);
	}

	@Override
	public Long getContainerCollectedQuantity(Long subId) throws Exception {
		String query =  "select sum(collected_quantity) from lt_customer_cont_collections where subs_id = ? ";
		return jdbcTemplate.queryForObject(query, new Object[] {subId }, Long.class);
	}

	@Override
	public List<LtCustomerContCollections> getCollectionQtyReport(LtCustomerContCollections input) throws Exception {
		String query = env.getProperty("getCollectionQtyReport");		
		List<LtCustomerContCollections> list = (List<LtCustomerContCollections>) 
				jdbcTemplate.query(query , new Object[]{ input.getProductId(), input.getUserId(), input.getSupplierId()
						  , input.getLength() , input.getStart() },
			 new  BeanPropertyRowMapper<LtCustomerContCollections>(LtCustomerContCollections.class));
		 return list;
	}

	@Override
	public Long getCollectionQtyReportCount(LtCustomerContCollections input) throws Exception {
		String query = env.getProperty("getCollectionQtyReportCount");		
		return	jdbcTemplate.queryForObject(query , new Object[]{ input.getProductId(), input.getUserId(), input.getSupplierId()  }, Long.class);
	}
	
	
	@Override
	public Double getCollectedBalanceQty(LtCustomerContCollections input, String isOwnContainer) throws Exception {
		String query = "";
		if( isOwnContainer != null &&  isOwnContainer.equalsIgnoreCase("Y") ) {
			query = env.getProperty("getDeliveredBalanceQty");		
		}else {
			query = env.getProperty("getCollectedBalanceQty");
		}
		return	jdbcTemplate.queryForObject(query , new Object[]{ input.getProductId() , input.getUserId(), input.getSupplierId()  }, Double.class);
	}

	@Override
	public LtCustomerContCollections getByDeliveryIdAndSupplierId(Long deliveryId, Long supplierId) throws Exception {
		String query = "select * from LT_CUSTOMER_CONT_COLLECTIONS where delivery_id = ? and supplier_id = ? ";		
		List<LtCustomerContCollections> list = (List<LtCustomerContCollections>) 
				jdbcTemplate.query(query , new Object[]{ deliveryId, supplierId },
			 new  BeanPropertyRowMapper<LtCustomerContCollections>(LtCustomerContCollections.class));
		 
		if(list != null & list.size() > 0 ) {
			return list.get(0);
		}
		return null;
	}
	 

}
