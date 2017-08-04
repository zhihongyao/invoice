package com.invoice.dao;

import java.util.List;

import com.invoice.entity.InvoiceTitle;

public interface InvoiceTitleDao {

	public List<InvoiceTitle> get(String openid);

	public Integer insert(InvoiceTitle invoiceTitle);
	
	public Integer update(InvoiceTitle invoiceTitle);
	
	public Integer updateDefault(String openid);
	
	public Integer delete(String id);
	
}