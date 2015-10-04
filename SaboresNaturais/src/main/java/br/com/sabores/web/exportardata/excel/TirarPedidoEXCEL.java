package br.com.sabores.web.exportardata.excel;

import java.util.Date;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Typed;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import br.com.sabores.ejb.model.Administrador;
import br.com.sabores.ejb.model.Itens;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.util.DateUtils;

@Typed(value=TirarPedidoEXCEL.class)
@Dependent
public class TirarPedidoEXCEL 
{
	
	@SuppressWarnings("unused")
	public void processarPlanilha(Object document,ListaDePedidos pedido,Administrador admin)
	{
		Integer rowCount = 0;
		Integer columnCount = 0;
		Double subtotal = 0D;
		Double subtotalPedido = 0D;
		
		HSSFWorkbook wb = (HSSFWorkbook)document;
		HSSFSheet sheet0 = wb.getSheetAt(0);
		wb.removeSheetAt(0);
		
		HSSFFont negritoItalico = wb.createFont();
		negritoItalico.setBold(true);
		negritoItalico.setItalic(true);
//		negritoItalico.setFontHeightInPoints((short)16);
		
		HSSFCellStyle styleCabecalho = wb.createCellStyle();
		styleCabecalho.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleCabecalho.setFont(negritoItalico);
		styleCabecalho.setBorderBottom((short)1);
		styleCabecalho.setBorderTop((short)1);
		styleCabecalho.setBorderLeft((short)1);
		styleCabecalho.setBorderRight((short)1);
		
		HSSFSheet sheet1 = wb.createSheet();
		HSSFRow cabecalhoGeral = sheet1.createRow(rowCount++);
		
		HSSFCell cell0CabecalhoGeral = cabecalhoGeral.createCell(columnCount++);
		HSSFCell cell1CabecalhoGeral = cabecalhoGeral.createCell(columnCount++);
		HSSFCell cell2CabecalhoGeral = cabecalhoGeral.createCell(columnCount++);
		HSSFCell cell3CabecalhoGeral = cabecalhoGeral.createCell(columnCount++);
		HSSFCell cell4CabecalhoGeral = cabecalhoGeral.createCell(columnCount++);
		columnCount = 0;
		
		cell0CabecalhoGeral.setCellValue(admin.getNomeFantasia().toUpperCase() + " - " + admin.getTelefoneContato());
		
		cell0CabecalhoGeral.setCellStyle(styleCabecalho);
		cell1CabecalhoGeral.setCellStyle(styleCabecalho);
		cell2CabecalhoGeral.setCellStyle(styleCabecalho);
		cell3CabecalhoGeral.setCellStyle(styleCabecalho);
		cell4CabecalhoGeral.setCellStyle(styleCabecalho);
		
		sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		
		HSSFFont negrito = wb.createFont();
		negrito.setBold(true);
//		negrito.setFontHeight((short) 14);
		
		HSSFCellStyle styleCabecalhoCliente = wb.createCellStyle();
		styleCabecalhoCliente.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleCabecalhoCliente.setFont(negrito);
		styleCabecalhoCliente.setBorderBottom((short)1);
		styleCabecalhoCliente.setBorderTop((short)1);
		styleCabecalhoCliente.setBorderLeft((short)1);
		styleCabecalhoCliente.setBorderRight((short)1);
		
		HSSFRow cabecalhoCliente = sheet1.createRow(rowCount++);
		HSSFCell cell0CabecalhoCliente = cabecalhoCliente.createCell(columnCount++);
		HSSFCell cell1CabecalhoCliente = cabecalhoCliente.createCell(columnCount++);
		HSSFCell cell2CabecalhoCliente = cabecalhoCliente.createCell(columnCount++);
		HSSFCell cell3CabecalhoCliente = cabecalhoCliente.createCell(columnCount++);
		HSSFCell cell4CabecalhoCliente = cabecalhoCliente.createCell(columnCount++);
		columnCount = 0;
		
		cell0CabecalhoCliente.setCellValue(pedido.getCliente().getNomeFantasia().toUpperCase());
		cell4CabecalhoCliente.setCellValue("COD.:");
		
		cell0CabecalhoCliente.setCellStyle(styleCabecalhoCliente);
		cell1CabecalhoCliente.setCellStyle(styleCabecalhoCliente);
		cell2CabecalhoCliente.setCellStyle(styleCabecalhoCliente);
		cell3CabecalhoCliente.setCellStyle(styleCabecalhoCliente);
		cell4CabecalhoCliente.setCellStyle(styleCabecalhoCliente);
		
		sheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
		
		HSSFRow tableHeader = sheet1.createRow(rowCount++);
		HSSFCell cell0TableHeader = tableHeader.createCell(columnCount++);
		HSSFCell cell1TableHeader = tableHeader.createCell(columnCount++);
		HSSFCell cell2TableHeader = tableHeader.createCell(columnCount++);
		HSSFCell cell3TableHeader = tableHeader.createCell(columnCount++);
		HSSFCell cell4TableHeader = tableHeader.createCell(columnCount++);
		columnCount = 0;
		
		HSSFCellStyle centralizado = wb.createCellStyle();
		centralizado.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		centralizado.setBorderBottom((short)1);
		centralizado.setBorderTop((short)1);
		centralizado.setBorderLeft((short)1);
		centralizado.setBorderRight((short)1);
		
		cell0TableHeader.setCellValue("CÓD.");
		cell1TableHeader.setCellValue("DESCRIÇÃO DO PRODUTO");
		cell2TableHeader.setCellValue("QUANT.");
		cell3TableHeader.setCellValue("UNITÁRIO");
		cell4TableHeader.setCellValue("TOTAL");
		
		cell0TableHeader.setCellStyle(centralizado);
		cell1TableHeader.setCellStyle(centralizado);
		cell2TableHeader.setCellStyle(centralizado);
		cell3TableHeader.setCellStyle(centralizado);
		cell4TableHeader.setCellStyle(centralizado);
		
		HSSFCellStyle somenteBordas = wb.createCellStyle();
		somenteBordas.setBorderBottom((short)1);
		somenteBordas.setBorderTop((short)1);
		somenteBordas.setBorderLeft((short)1);
		somenteBordas.setBorderRight((short)1);
		
		for (Itens it : pedido.getItens()) 
		{
			HSSFRow tableBody = sheet1.createRow(rowCount++);
			HSSFCell cell0TableBody = tableBody.createCell(columnCount++);
			HSSFCell cell1TableBody = tableBody.createCell(columnCount++);
			HSSFCell cell2TableBody = tableBody.createCell(columnCount++);
			HSSFCell cell3TableBody = tableBody.createCell(columnCount++);
			HSSFCell cell4TableBody = tableBody.createCell(columnCount++);
			columnCount = 0;
			
			subtotal = 0D;
			subtotal = it.getQuantidade() * it.getPreco();
			subtotalPedido += subtotal;
			
			cell0TableBody.setCellValue(it.getProduto().getCodigoProduto());
			cell1TableBody.setCellValue(it.getProduto().getDescricao());
			cell2TableBody.setCellValue(it.getQuantidade());
			cell3TableBody.setCellValue(it.getPreco());
			cell4TableBody.setCellValue(subtotal);
			
			cell0TableBody.setCellStyle(centralizado);
			cell1TableBody.setCellStyle(somenteBordas);
			cell2TableBody.setCellStyle(centralizado);
			cell3TableBody.setCellStyle(centralizado);
			cell4TableBody.setCellStyle(centralizado);
		}
		
		while(rowCount < 12)
		{
			HSSFRow tableBody = sheet1.createRow(rowCount++);
			HSSFCell cell0TableBody = tableBody.createCell(columnCount++);
			HSSFCell cell1TableBody = tableBody.createCell(columnCount++);
			HSSFCell cell2TableBody = tableBody.createCell(columnCount++);
			HSSFCell cell3TableBody = tableBody.createCell(columnCount++);
			HSSFCell cell4TableBody = tableBody.createCell(columnCount++);
			columnCount = 0;
			
			cell0TableBody.setCellValue("");
			cell1TableBody.setCellValue("");
			cell2TableBody.setCellValue("");
			cell3TableBody.setCellValue("");
			cell4TableBody.setCellValue("-");
			
			cell0TableBody.setCellStyle(somenteBordas);
			cell1TableBody.setCellStyle(somenteBordas);
			cell2TableBody.setCellStyle(somenteBordas);
			cell3TableBody.setCellStyle(somenteBordas);
			cell4TableBody.setCellStyle(centralizado);
		}
		
		HSSFFont negrito14 = wb.createFont();
		negrito14.setBold(true);
//		negrito14.setFontHeight((short)14);
		
		HSSFCellStyle footer = wb.createCellStyle();
		footer.setFont(negrito14);
		footer.setBorderBottom((short)1);
		footer.setBorderTop((short)1);
		footer.setBorderLeft((short)1);
		footer.setBorderRight((short)1);
		
		HSSFCellStyle footerCentralizado = wb.createCellStyle();
		footerCentralizado.setFont(negrito14);
		footerCentralizado.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		footerCentralizado.setBorderBottom((short)1);
		footerCentralizado.setBorderTop((short)1);
		footerCentralizado.setBorderLeft((short)1);
		footerCentralizado.setBorderRight((short)1);
		
		HSSFRow tableFooter1 = sheet1.createRow(rowCount);
		HSSFCell cell0TableFooter1 = tableFooter1.createCell(columnCount++);
		HSSFCell cell1TableFooter1 = tableFooter1.createCell(columnCount++);
		HSSFCell cell2TableFooter1 = tableFooter1.createCell(columnCount++);
		HSSFCell cell3TableFooter1 = tableFooter1.createCell(columnCount++);
		HSSFCell cell4TableFooter1 = tableFooter1.createCell(columnCount++);
		columnCount = 0;
		
		cell0TableFooter1.setCellValue(admin.getNome().toUpperCase());
		cell1TableFooter1.setCellValue("");
		cell2TableFooter1.setCellValue("");
		cell3TableFooter1.setCellValue("TOTAL");
		cell4TableFooter1.setCellValue(subtotalPedido);
		
		cell0TableFooter1.setCellStyle(footer);
		cell1TableFooter1.setCellStyle(somenteBordas);
		cell2TableFooter1.setCellStyle(somenteBordas);
		cell3TableFooter1.setCellStyle(footerCentralizado);
		cell4TableFooter1.setCellStyle(footerCentralizado);
		
		sheet1.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 2));
		rowCount++;
		
		HSSFRow tableFooter2 = sheet1.createRow(rowCount);
		HSSFCell cell0TableFooter2 = tableFooter2.createCell(columnCount++);
		HSSFCell cell1TableFooter2 = tableFooter2.createCell(columnCount++);
		HSSFCell cell2TableFooter2 = tableFooter2.createCell(columnCount++);
		HSSFCell cell3TableFooter2 = tableFooter2.createCell(columnCount++);
		HSSFCell cell4TableFooter2 = tableFooter2.createCell(columnCount++);
		columnCount = 0;
		
		Date data = pedido.getDataAlteracao();
		
		cell0TableFooter2.setCellValue(DateUtils.formatarDate(data));
		cell1TableFooter2.setCellValue("");
		cell2TableFooter2.setCellValue(pedido.getPrazo());
		cell3TableFooter2.setCellValue("VENC");
		cell4TableFooter2.setCellValue(DateUtils.formatarDate(DateUtils.somarDatas(data, pedido.getPrazo())));
		
		cell0TableFooter2.setCellStyle(footer);
		cell1TableFooter2.setCellStyle(somenteBordas);
		cell2TableFooter2.setCellStyle(footerCentralizado);
		cell3TableFooter2.setCellStyle(footerCentralizado);
		cell4TableFooter2.setCellStyle(footer);
		
		sheet1.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 1));
		sheet1.setDisplayGridlines(true);
		sheet1.setGridsPrinted(true);
		
	}
	
}
