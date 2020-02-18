package com.redcrystal.example.controller.demo;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@ManagedBean
@Controller
@Scope(value = "view")
public class ChartView implements Serializable {

	/**
	 * generated serial version id
	 */
	private static final long serialVersionUID = 5904600352847784838L;

	/** The line chart model */
	private LineChartModel lineChart;

	/** The bar chart model */
	private BarChartModel barChart;

	/** the pie chart model */
	private PieChartModel pieChart;

	/**
	 * to initialize data
	 */
	@PostConstruct
	public void init() {
		initLineChart();
		initBarChart();
		initPieChart();
	}

	/**
	 * to initialize pie chart
	 */
	private void initPieChart() {
		pieChart = new PieChartModel();
		pieChart.setShowDataLabels(true);

		pieChart.setLegendPosition("e");
		pieChart.set("Brand 1", 540);
		pieChart.set("Brand 2", 325);
		pieChart.set("Brand 3", 702);
		pieChart.set("Brand 4", 421);

		pieChart.setTitle("Pie Chart");
	}

	/**
	 * to initialize bar chart
	 */
	private void initBarChart() {
		barChart = new BarChartModel();
		barChart.setTitle("Bar Chart");
		barChart.setAnimate(true);
		barChart.setLegendPosition("ne");
		barChart.setSeriesColors("00FF00, FF0000");
		barChart.setShowDatatip(true);
		barChart.setShowPointLabels(true);

		/** Axis X and Y */
		Axis yAxis = barChart.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(200);
		yAxis.setLabel("Tests");

		Axis xAxis = barChart.getAxis(AxisType.X);
		xAxis.setLabel("Device");

		ChartSeries pass = new ChartSeries();
		pass.setLabel("Pass");
		pass.set("Q5", 120);
		pass.set("Q10", 100);
		pass.set("Z10", 44);
		pass.set("Z30", 150);
		pass.set("Z3", 25);

		ChartSeries fail = new ChartSeries();
		fail.setLabel("Fail");
		fail.set("Q5", 52);
		fail.set("Q10", 60);
		fail.set("Z10", 110);
		fail.set("Z30", 135);
		fail.set("Z3", 120);

		barChart.addSeries(pass);
		barChart.addSeries(fail);
	}

	/**
	 * to initialize line chart
	 */
	private void initLineChart() {
		lineChart = new LineChartModel();
		lineChart.setTitle("Line Chart");
		lineChart.setAnimate(true);
		lineChart.setLegendPosition("se");
		/** Axis X and Y */
		Axis yAxis = lineChart.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(10);

		Axis xAxis = lineChart.getAxis(AxisType.X);
		xAxis.setTickAngle(-30);

		/** Chart series */
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Series 1");
		series1.set(1, 2);
		series1.set(2, 1);
		series1.set(3, 3);
		series1.set(4, 6);
		series1.set(5, 8);

		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Series 2");
		series2.set(1, 6);
		series2.set(2, 3);
		series2.set(3, 2);
		series2.set(4, 7);
		series2.set(5, 9);

		lineChart.addSeries(series1);
		lineChart.addSeries(series2);
	}

	/**
	 * @return the lineChart
	 */
	public LineChartModel getLineChart() {
		return lineChart;
	}

	/**
	 * @param lineChart
	 *            the lineChart to set
	 */
	public void setLineChart(LineChartModel lineChart) {
		this.lineChart = lineChart;
	}

	/**
	 * @return the barChart
	 */
	public BarChartModel getBarChart() {
		return barChart;
	}

	/**
	 * @param barChart
	 *            the barChart to set
	 */
	public void setBarChart(BarChartModel barChart) {
		this.barChart = barChart;
	}

	/**
	 * @return the pieChart
	 */
	public PieChartModel getPieChart() {
		return pieChart;
	}

	/**
	 * @param pieChart
	 *            the pieChart to set
	 */
	public void setPieChart(PieChartModel pieChart) {
		this.pieChart = pieChart;
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Index: " + event.getItemIndex() + ", Series Index:"
				+ event.getSeriesIndex());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
