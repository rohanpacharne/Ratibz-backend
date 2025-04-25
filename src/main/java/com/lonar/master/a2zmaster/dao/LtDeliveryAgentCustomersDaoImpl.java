package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.DeliveryAgentCustomers;
//import com.lonar.a2zcommons.model.LtDeliveryAgentCustomers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.DeliveryAgentCustomers;
import com.lonar.master.a2zmaster.model.LtDeliveryAgentCustomers;
import com.lonar.master.a2zmaster.repository.LtDeliveryAgentCustomersRepository;

@Repository
@PropertySource(value = "classpath:queries/deliveryAgentCustQueries.properties", ignoreResourceNotFound = true)
public class LtDeliveryAgentCustomersDaoImpl implements LtDeliveryAgentCustomersDao, CodeMaster {

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
	LtDeliveryAgentCustomersRepository ltDeliveryAgentCustomersRepository;

	@Override
	public Long getDeliveryAgentCustomersCount(LtDeliveryAgentCustomers input, Long supplierId)
			throws ServiceException {
		String query = env.getProperty("getDeliveryAgentCustomersCount");

		String status = null;
		if (input.getStatus() != null && !input.getStatus().equals("")) {
			status = "%" + input.getStatus().toUpperCase().trim() + "%";
		}

		if (input.getStDate() == null || input.getStDate().trim().equals("")) {
			input.setStDate(null);
		}

		if (input.getEnDate() == null || input.getEnDate().trim().equals("")) {
			input.setEnDate(null);
		}

		String count = (String) getJdbcTemplate().queryForObject(query,
				new Object[] { supplierId, status, input.getStDate(), input.getEnDate() }, String.class);

		return Long.parseLong(count);
	}

	@Override
	public List<LtDeliveryAgentCustomers> getDeliveryAgentCustomersDataTable(LtDeliveryAgentCustomers input,
			Long supplierId) throws ServiceException {
		if (input.getSort() == null) {
			input.setSort("desc");
		}

		String status = null;
		if (input.getStatus() != null && !input.getStatus().equals("")) {
			status = "%" + input.getStatus().toUpperCase().trim() + "%";
		}

		if (input.getStDate() == null || input.getStDate().trim().equals("")) {
			input.setStDate(null);
		}

		if (input.getEnDate() == null || input.getEnDate().trim().equals("")) {
			input.setEnDate(null);
		}

		if (input.getColumnNo() == 0) {
			input.setColumnNo(5);
		}
		String query = env.getProperty("getLtProductTypesDataTable");

		return (List<LtDeliveryAgentCustomers>) jdbcTemplate.query(query,
				new Object[] { supplierId, status, input.getStDate(), input.getEnDate(), input.getColumnNo(),
						input.getColumnNo(), input.getColumnNo(), input.getColumnNo(), input.getColumnNo(),
						input.getColumnNo(), input.getColumnNo(), input.getColumnNo(), input.getColumnNo(),
						input.getColumnNo(),

						input.getLength() + input.getStart(), input.getStart() },
				new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));
	}

	@Override
	public LtDeliveryAgentCustomers save(LtDeliveryAgentCustomers ltDeliveryAgentCustomers) throws ServiceException {
		return ltDeliveryAgentCustomersRepository.save(ltDeliveryAgentCustomers);
	}

	@Override
	public LtDeliveryAgentCustomers getLtDeliveryAgentCustomers(Long deliveryAgentCustId) throws ServiceException {
		String query = env.getProperty("getLtDeliveryAgentCustomers");
		LtDeliveryAgentCustomers ltDeliveryAgentCustomers = jdbcTemplate.queryForObject(query,
				new Object[] { deliveryAgentCustId },
				new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));
		return ltDeliveryAgentCustomers;
	}

	@Override
	public List<LtDeliveryAgentCustomers> getAllActiveDeliveryAgentCustomers(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveDeliveryAgentCustomers");
		List<LtDeliveryAgentCustomers> deliveryAgentCustomersList = jdbcTemplate.query(query,
				new Object[] { supplierId },
				new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));
		return deliveryAgentCustomersList;
	}

	@Override
	public LtDeliveryAgentCustomers delete(Long deliveryAgentCustId) throws ServiceException {
		ltDeliveryAgentCustomersRepository.deleteById(deliveryAgentCustId);
		if (ltDeliveryAgentCustomersRepository.existsById(deliveryAgentCustId)) {
			return ltDeliveryAgentCustomersRepository.findById(deliveryAgentCustId).get();
		} else
			return null;
	}

	@Override
	public List<LtDeliveryAgentCustomers> getAllAssignedCustomers(Long userId) throws ServiceException {
		String query = env.getProperty("getAllAssignedCustomersForDeliveryAgent");
		List<LtDeliveryAgentCustomers> deliveryAgentCustomersList = jdbcTemplate.query(query, new Object[] { userId },
				new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));
		return deliveryAgentCustomersList;
	}

	@Override
	public List<LtDeliveryAgentCustomers> assignCustToAgent(List<LtDeliveryAgentCustomers> ltDeliveryAgentCustomerList)
			throws ServiceException {
		return (List<LtDeliveryAgentCustomers>) ltDeliveryAgentCustomersRepository.saveAll(ltDeliveryAgentCustomerList);

	}

	@Override
	public DeliveryAgentCustomers getDeliveryAgentInfoByUserId(Long userId) throws ServiceException {
		String query = env.getProperty("getDeliveryAgentInfoByUserId");
		List<DeliveryAgentCustomers> deliveryAgentCustomersList = jdbcTemplate.query(query, new Object[] { userId },
				new BeanPropertyRowMapper<DeliveryAgentCustomers>(DeliveryAgentCustomers.class));
		if (!deliveryAgentCustomersList.isEmpty())
			return deliveryAgentCustomersList.get(0);
		else
			return null;
	}

	@Override
	public boolean deleteAllAssignedCustByUser(Long userId, Long supplierId) throws ServiceException {
		String query = env.getProperty("deleteAllAssignedCustByUser");
		int result = jdbcTemplate.update(query, userId, supplierId);
		if (result != 0)
			return true;
		else
			return false;

	}

	@Override
	public LtDeliveryAgentCustomers getLtDeliveryAgentCustomersByCustId(Long customerId) throws ServiceException {
		String query = env.getProperty("getLtDeliveryAgentCustomersByCustId");
		LtDeliveryAgentCustomers ltDeliveryAgentCustomers = jdbcTemplate.queryForObject(query,
				new Object[] { customerId },
				new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));
		if (ltDeliveryAgentCustomers != null)
			return ltDeliveryAgentCustomers;
		else
			return null;
	}

	@Override
	public List<LtDeliveryAgentCustomers> getAllUnAssignedCustomers(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllUnAssignedCustomers");
		List<LtDeliveryAgentCustomers> deliveryAgentCustomersList = jdbcTemplate.query(query,
				new Object[] { supplierId },
				new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));
		return deliveryAgentCustomersList;
	}

	@Override
	public Long getAllCustomersCount(LtDeliveryAgentCustomers input, Long supplierId,Long deliveryAgentId) throws ServiceException {
		

		String user = null;
		if (input.getStatus() != null && !input.getStatus().equals("")) {
			user = "%" + input.getStatus().toUpperCase().trim() + "%";
		}
		
		/*int asigned = 0;
		if(input.getUnasigned() != null && input.getUnasigned() == true ) {
			asigned = 1;
		}		*/

		if( input.getUnasigned()) {
			String query = env.getProperty("get_unasigned_customers_count");
			return   getJdbcTemplate().queryForObject(query,
					new Object[] { supplierId,  user }, Long.class);
		}else {
			String query = env.getProperty("get_asigned_customers_to_other_agent_count");
			return getJdbcTemplate().queryForObject(query,
					new Object[] { supplierId, deliveryAgentId, user }, Long.class);
		}
		
/*		String query = env.getProperty("getAllLtDeliveryAgentCustomersCountV1");
 *         String count = (String) getJdbcTemplate().queryForObject(query,
				new Object[] { supplierId, asigned ,deliveryAgentId, status }, String.class);
		return Long.parseLong(count);*/
	}

	@Override
	public List<LtDeliveryAgentCustomers> getAllCustomersData(LtDeliveryAgentCustomers input, Long supplierId,Long deliveryAgentId)
			throws ServiceException {
		String user = null;
		
		if (input.getStatus() != null && !input.getStatus().equals("")) {
			user = "%" + input.getStatus().toUpperCase().trim() + "%";
		}
		
		/*int asigned = 0;
		if(input.getUnasigned() != null && input.getUnasigned() == true ) {
			asigned = 1;
		}*/		
		
		if( input.getUnasigned()) {
			String query = env.getProperty("get_unasigned_customers");
			return (List<LtDeliveryAgentCustomers>) jdbcTemplate.query(query,
					new Object[] { supplierId, user, input.getLength(), input.getStart() },
					new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));
		}else {
			String query = env.getProperty("get_asigned_customers_to_other_agent");
			return (List<LtDeliveryAgentCustomers>) jdbcTemplate.query(query,
					new Object[] { supplierId, deliveryAgentId, user, input.getLength(), input.getStart() },
					new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));
		}
		// 
		/*String query = env.getProperty("getAllLtDeliveryAgentCustomersV1");
		return (List<LtDeliveryAgentCustomers>) jdbcTemplate.query(query,
				new Object[] { supplierId, asigned, deliveryAgentId, user, input.getLength(), input.getStart() },
				new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));*/
	}
	
	
	@Override
	public String getDeliveryAgentName(Long customerId) throws ServiceException {
		StringBuffer deliveryAgentName = new StringBuffer();
		String query = env.getProperty("getDeliveryAgentName");
		List<LtDeliveryAgentCustomers> list = jdbcTemplate.query(query,
				new Object[] { customerId },
				new BeanPropertyRowMapper<LtDeliveryAgentCustomers>(LtDeliveryAgentCustomers.class));
		if(!list.isEmpty()) {
			int i = 1;
			for(LtDeliveryAgentCustomers ltDeliveryAgentCustomers  :list) {
				if( list.size() ==  i ) {
					deliveryAgentName.append(ltDeliveryAgentCustomers.getDeliveryAgentName());	
				}else {
					deliveryAgentName.append(ltDeliveryAgentCustomers.getDeliveryAgentName()+", ");
				}
				++i;
			}
			/*list.forEach((ltDeliveryAgentCustome+rs) -> {
				deliveryAgentName = deliveryAgentName.append(ltDeliveryAgentCustomers.getDeliveryAgentName());
			});*/
			return deliveryAgentName.toString();
		}else
			return null;
	}

	@Override
	public Long getAllUnAssignedCustomersCount(Long supplierId) throws Exception {
		String query = env.getProperty("getAllUnAssignedCustomersCount");
		return jdbcTemplate.queryForObject(query, new Object[]{ supplierId}, Long.class);
	}

}
