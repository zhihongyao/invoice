package com.invoice.service;

import java.util.List;

import com.invoice.entity.InvoiceTitle;

public interface InvoiceTitleService {
 
	public Integer add(InvoiceTitle invoiceTitle);

	public List<InvoiceTitle> get(String openid);

	public Integer update(InvoiceTitle invoiceTitle);

	public Integer delete(String id);
}