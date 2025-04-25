package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.repository.LtSubscrptionOfferRepository;

@Repository
@PropertySource(value = "classpath:queries/SubscrptionOffer.properties", ignoreResourceNotFound = true)
public class LtSubscrptionOfferDaoImpl implements LtSubscrptionOfferDao {

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
	LtSubscrptionOfferRepository ltSubscrptionOfferRepository;

	@Override
	public Long getLtSubscrptionOfferCount(LtSubscrptionOffer input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtSubscrptionOfferCount");

		String name = null;
		if (input.getOfferDetails() != null && !input.getOfferDetails().equals("")) {
			name = "%" + input.getOfferDetails().trim().toUpperCase() + "%";
		}
		String count = (String) getJdbcTemplate().queryForObject(query, new Object[] { supplierId, name },
				String.class);
		return Long.parseLong(count);
	}

	@Override
	public List<LtSubscrptionOffer> getLtSubscrptionOfferDataTable(LtSubscrptionOffer input, Long supplierId)
			throws ServiceException {
		if (input.getSort() == null) {
			input.setSort("desc");
		}
		if (input.getColumnNo() == null || input.getColumnNo() == 0) {
			input.setColumnNo(1);
		}

		String name = null;
		if (input.getOfferDetails() != null && !input.getOfferDetails().equals("")) {
			name = "%" + input.getOfferDetails().trim().toUpperCase() + "%";
		}

		String query = env.getProperty("getLtSubscrptionOfferDataTable");

		return (List<LtSubscrptionOffer>) jdbcTemplate
				.query(query,
						new Object[] { supplierId, name, input.getColumnNo(), input.getLength() ,input.getStart() },
						new BeanPropertyRowMapper<LtSubscrptionOffer>(LtSubscrptionOffer.class));
	}

	@Override
	public LtSubscrptionOffer save(LtSubscrptionOffer ltSubscrptionOffer) throws ServiceException {
		return ltSubscrptionOfferRepository.save(ltSubscrptionOffer);
	}

	@Override
	public LtSubscrptionOffer getSubscrptionOfferById(Long offerId) throws ServiceException {
		String query = env.getProperty("getSubscrptionOfferById");
		LtSubscrptionOffer ltSubscrptionOffer = jdbcTemplate.queryForObject(query, new Object[] { offerId },
				new BeanPropertyRowMapper<LtSubscrptionOffer>(LtSubscrptionOffer.class));
		return ltSubscrptionOffer;
	}

	@Override
	public List<LtSubscrptionOffer> getAllActiveOffers(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveSubscrptionOffer");
		List<LtSubscrptionOffer> ltProductsList = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtSubscrptionOffer>(LtSubscrptionOffer.class));
		return ltProductsList;
	}

	@Override
	public LtSubscrptionOffer delete(Long offerId) throws ServiceException {
		ltSubscrptionOfferRepository.deleteById(offerId);
		if (ltSubscrptionOfferRepository.existsById(offerId)) {
			return ltSubscrptionOfferRepository.findById(offerId).get();
		} else
			return null;
	}

	@Override
	public LtSubscrptionOffer getLtSubscrptionOfferByCode(Long supplierId,String offerCode) throws ServiceException {
		String query = env.getProperty("getLtSubscrptionOfferByCode");
		List<LtSubscrptionOffer> ltSubscrptionOfferList = jdbcTemplate.query(query,
				new Object[] { supplierId,offerCode },
				new BeanPropertyRowMapper<LtSubscrptionOffer>(LtSubscrptionOffer.class));
		if (!ltSubscrptionOfferList.isEmpty())
			return ltSubscrptionOfferList.get(0);
		else
			return null;
	}

	@Override
	public List<LtSubscrptionOffer> getAllActiveOffersCode(Long supplierId, String code) throws ServiceException {
		String query = env.getProperty("getAllActiveOffersCode");
		List<LtSubscrptionOffer> ltProductsList = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtSubscrptionOffer>(LtSubscrptionOffer.class));
		return ltProductsList;
	}

	@Override
	public LtSubscrptionOffer checkOfferValidity(LtSubscrptionOffer ltSubscrptionOffer) throws ServiceException {
		String query = env.getProperty("checkOfferValidity");
		List<LtSubscrptionOffer> ltSubscrptionOfferList = jdbcTemplate.query(query,
				new Object[] { ltSubscrptionOffer.getSupplierId(),ltSubscrptionOffer.getOfferId() },
				new BeanPropertyRowMapper<LtSubscrptionOffer>(LtSubscrptionOffer.class));
		if (!ltSubscrptionOfferList.isEmpty())
			return ltSubscrptionOfferList.get(0);
		else
			return null;
	}

}
