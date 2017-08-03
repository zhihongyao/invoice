package com.invoice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoice.dao.InvoiceTitleDao;
import com.invoice.entity.InvoiceTitle;
import com.invoice.service.InvoiceTitleService;

@Service
public class InvoiceTitleServiceImpl implements InvoiceTitleService {
	
	protected final static Logger logger = LoggerFactory.getLogger(InvoiceTitleServiceImpl.class);
	@Autowired
	private InvoiceTitleDao invoiceTitleDao;
	
	@Override
	public Integer add(InvoiceTitle invoiceTitle) {
		if (invoiceTitle.getIsdefault().equals("1")) {
			invoiceTitleDao.updateDefault(invoiceTitle.getOpenid());
		}
		return invoiceTitleDao.insert(invoiceTitle);
		
	}
	@Override
	public List<InvoiceTitle> get(String openid) {
		return invoiceTitleDao.get(openid);
	}
	@Override
	public Integer update(InvoiceTitle invoiceTitle) {
		if (invoiceTitle.getIsdefault().equals("1")) {
			invoiceTitleDao.updateDefault(invoiceTitle.getOpenid());
		}
		return invoiceTitleDao.update(invoiceTitle);
	}
	@Override
	public Integer delete(String id) {
		return invoiceTitleDao.delete(id);
	}

}