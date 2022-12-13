/*******************************************************************************
 * Copyright (c) 2020-2022,  Olivier Debenath
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Olivier Debenath <olivier@debenath.ch> - initial implementation
 *    
 *******************************************************************************/
package ch.framsteg.hl7.sysmex.server.messages;

import org.apache.log4j.Logger;

/* Representing the original XPMessage */
public class XPMessage {

	private final static String DASH = "--";
	private final static String TITLE = "Generate XPMessage";
	
	private final static String LOG_EXTRACTION_1 = "Extract 'Text Distinction Code1' at (0,1): ";
	private final static String LOG_EXTRACTION_2 = "Extract 'Text Distinction Code2' at (1,2): ";
	private final static String LOG_EXTRACTION_3 = "Extract 'Sample Distinction Code' at (2,3): ";
	private final static String LOG_EXTRACTION_4 = "Extract 'Instrument ID' at (3,43): ";
	private final static String LOG_EXTRACTION_5 = "Extract 'Year' at (43,47): ";
	private final static String LOG_EXTRACTION_6 = "Extract 'Month' at (47,49): ";
	private final static String LOG_EXTRACTION_7 = "Extract 'Day' at (49,51): ";
	private final static String LOG_EXTRACTION_8 = "Extract 'Analysis Status' at (51,52): ";
	private final static String LOG_EXTRACTION_9 = "Extract 'Sample ID' at (52,67): ";
	private final static String LOG_EXTRACTION_10 = "Extract 'Particle Size' at (67,73): ";
	private final static String LOG_EXTRACTION_11 = "Extract 'Reserve Bit' at (73,74): ";
	private final static String LOG_EXTRACTION_12 = "Extract 'WBC' at (74,79): ";
	private final static String LOG_EXTRACTION_13 = "Extract 'RBC' at (79,84): ";
	private final static String LOG_EXTRACTION_14 = "Extract 'HGB' at (84,89): ";
	private final static String LOG_EXTRACTION_15 = "Extract 'HCT' at (89,94): ";
	private final static String LOG_EXTRACTION_16 = "Extract 'MCV' at (94,99): ";
	private final static String LOG_EXTRACTION_17 = "Extract 'MCH' at (99,104): ";
	private final static String LOG_EXTRACTION_18 = "Extract 'MCHC' at (104,109): ";
	private final static String LOG_EXTRACTION_19 = "Extract 'PLT' at (109,114): ";
	private final static String LOG_EXTRACTION_20 = "Extract 'LYM%' at (114,119): ";
	private final static String LOG_EXTRACTION_21 = "Extract 'MXD%' at (119,119): ";
	private final static String LOG_EXTRACTION_22 = "Extract 'NEUT%' at (124,129): ";
	private final static String LOG_EXTRACTION_23 = "Extract 'LYM#' at (129,134): ";
	private final static String LOG_EXTRACTION_24 = "Extract 'MXD#' at (134,139): ";
	private final static String LOG_EXTRACTION_25 = "Extract 'NEUT#' at (139,144): ";
	private final static String LOG_EXTRACTION_26 = "Extract 'RDW-SD' at (144,149): ";
	private final static String LOG_EXTRACTION_27 = "Extract 'RDW-CV' at (149,154): ";
	private final static String LOG_EXTRACTION_28 = "Extract 'PDW' at (154,159): ";
	private final static String LOG_EXTRACTION_29 = "Extract 'MPV' at (159,164): ";
	private final static String LOG_EXTRACTION_30 = "Extract 'P-LCR' at (164,169): ";
	private final static String LOG_EXTRACTION_31 = "Extract 'PCT' at (169,174): ";
	
	private final static String MSG_PART_1 = "STX: ";
	private final static String MSG_PART_2 = "Text Distinction Code 1: ";
	private final static String MSG_PART_3 = "Text Distinction Code 2: ";
	private final static String MSG_PART_4 = "Sample Distinction Code: ";
	private final static String MSG_PART_5 = "Instrument ID: ";
	private final static String MSG_PART_6 = "Year: ";
	private final static String MSG_PART_7 = "Month: ";
	private final static String MSG_PART_8 = "Day: ";
	private final static String MSG_PART_9 = "Analysis Status: ";
	private final static String MSG_PART_10 = "Sample ID: ";
	private final static String MSG_PART_11 = "Particle Size: ";
	private final static String MSG_PART_12 = "Reserve: ";
	private final static String MSG_PART_13 = "WBC: ";
	private final static String MSG_PART_14 = "RBC: ";
	private final static String MSG_PART_15 = "HGB: ";
	private final static String MSG_PART_16 = "HCT: ";
	private final static String MSG_PART_17 = "MCV: ";
	private final static String MSG_PART_18 = "MCH: ";
	private final static String MSG_PART_19 = "MCHC: ";
	private final static String MSG_PART_20 = "PLT: ";
	private final static String MSG_PART_21 = "W SCR (LYM%): ";
	private final static String MSG_PART_22 = "W MCR (MXD%): ";
	private final static String MSG_PART_23 = "W LCR (NEUT%): ";
	private final static String MSG_PART_24 = "W SCC (LYM#): ";
	private final static String MSG_PART_25 = "W MCC (MXD#): ";
	private final static String MSG_PART_26 = "W LCC (NEUT#): ";
	private final static String MSG_PART_27 = "RDW_SD: ";
	private final static String MSG_PART_28 = "RDW_CV ";
	private final static String MSG_PART_29 = "PDW: ";
	private final static String MSG_PART_30 = "MPV: ";
	private final static String MSG_PART_31 = "P_LCR: ";
	private final static String MSG_PART_32 = "PCT: ";
	
	

	private String input;

	private String stx;
	private String textDistinctionCode1;
	private String textDistinctionCode2;
	private String sampleDistinctionCode;
	private String instrumentID;
	private String year;
	private String month;
	private String day;
	private String analysisStatus;
	private String sampleID;
	private String particleSize;
	private String reserve;
	private String wbc;
	private String rbc;
	private String hgb;
	private String hct;
	private String mcv;
	private String mch;
	private String mchc;
	private String plt;
	private String w_scr;
	private String w_mcr;
	private String w_lcr;
	private String w_scc;
	private String w_mcc;
	private String w_lcc;
	private String rdw_sd;
	private String rdw_cv;
	private String pdw;
	private String mpv;
	private String p_lcr;
	private String pct;
	private String ext;

	private static Logger logger = Logger.getLogger(XPMessage.class);

	public XPMessage(String input) {
		this.input = input;
	}

	public void build() {
		logger.info(DASH);
		logger.info(TITLE);
		// Text Distinction Code1
		setTextDistinctionCode1(input.substring(0, 1));
		logger.info(LOG_EXTRACTION_1 + getTextDistinctionCode1());
		// Text Distinction Code2
		setTextDistinctionCode2(input.substring(1, 2));
		logger.info(LOG_EXTRACTION_2 + getTextDistinctionCode2());
		// Sample Distinction Code
		setSampleDistinctionCode(input.substring(2, 3));
		logger.info(LOG_EXTRACTION_3 + getSampleDistinctionCode());
		// Instrument ID
		setInstrumentID(input.substring(3, 43));
		logger.info(LOG_EXTRACTION_4 + getInstrumentID());
		// Year
		setYear(input.substring(43, 47));
		logger.info(LOG_EXTRACTION_5 + getYear());
		// Month
		setMonth(input.substring(47, 49));
		logger.info(LOG_EXTRACTION_6 + getMonth());
		// Day
		setDay(input.substring(49, 51));
		logger.info(LOG_EXTRACTION_7 + getDay());
		// Analysis Status
		setAnalysisStatus(input.substring(51, 52));
		logger.info(LOG_EXTRACTION_8 + getAnalysisStatus());
		// Sample ID
		setSampleID(input.substring(52, 67));
		logger.info(LOG_EXTRACTION_9 + getSampleID());
		// Particle Size
		setParticleSize(input.substring(67, 73));
		logger.info(LOG_EXTRACTION_10 + getParticleSize());
		// Reserve
		setReserve(input.substring(73, 74));
		logger.info(LOG_EXTRACTION_11 + getReserve());
		// WBC
		setWbc(input.substring(74, 79));
		logger.info(LOG_EXTRACTION_12 + getWbc());
		// RBC
		setRbc(input.substring(79, 84));
		logger.info(LOG_EXTRACTION_13 + getRbc());
		// HGB
		setHgb(input.substring(84, 89));
		logger.info(LOG_EXTRACTION_14 + getHgb());
		// HCT
		setHct(input.substring(89, 94));
		logger.info(LOG_EXTRACTION_15 + getHct());
		// MCV
		setMcv(input.substring(94, 99));
		logger.info(LOG_EXTRACTION_16 + getMcv());
		// MCH
		setMch(input.substring(99, 104));
		logger.info(LOG_EXTRACTION_17 + getMch());
		// MCHC
		setMchc(input.substring(104, 109));
		logger.info(LOG_EXTRACTION_18 + getMchc());
		// PLT
		setPlt(input.substring(109, 114));
		logger.info(LOG_EXTRACTION_19 + getPlt());
		// LYM%
		setW_scr(input.substring(114, 119));
		logger.info(LOG_EXTRACTION_20 + getW_scr());
		// MXD%
		setW_mcr(input.substring(119, 124));
		logger.info(LOG_EXTRACTION_21 + getW_mcr());
		// NEUT%
		setW_lcr(input.substring(124, 129));
		logger.info(LOG_EXTRACTION_22 + getW_lcr());
		// LYM#
		setW_scc(input.substring(129, 134));
		logger.info(LOG_EXTRACTION_23 + getW_scc());
		// MXD#
		setW_mcc(input.substring(134, 139));
		logger.info(LOG_EXTRACTION_24 + getW_mcc());
		// NEUT#
		setW_lcc(input.substring(139, 144));
		logger.info(LOG_EXTRACTION_25 + getW_lcc());
		// RDW-SD
		setRdw_sd(input.substring(144, 149));
		logger.info(LOG_EXTRACTION_26 + getRdw_sd());
		// RDW-CV
		setRdw_cv(input.substring(149, 154));
		logger.info(LOG_EXTRACTION_27 + getRdw_cv());
		// PDW
		setPdw(input.substring(154, 159));
		logger.info(LOG_EXTRACTION_28 + getPdw());
		// MPV
		setMpv(input.substring(159, 164));
		logger.info(LOG_EXTRACTION_29 + getMpv());
		// P-LCR
		setP_lcr(input.substring(164, 169));
		logger.info(LOG_EXTRACTION_30 + getP_lcr());
		// PCT
		setPct(input.substring(169, 174));
		logger.info(LOG_EXTRACTION_31 + getPct());
	}

	@Override
	public String toString() {
		StringBuilder messageProperties = new StringBuilder();
		messageProperties.append(MSG_PART_1 + getStx());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_2 + getTextDistinctionCode1());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_3 + getTextDistinctionCode2());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_4 + getSampleDistinctionCode());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_5 + getInstrumentID());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_6 + getYear());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_7 + getMonth());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_8 + getDay());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_9 + getAnalysisStatus());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_10 + getSampleID());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_11 + getParticleSize());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_12 + getReserve());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_13 + getWbc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_14 + getRbc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_15 + getHgb());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_16 + getHct());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_17 + getMcv());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_18 + getMch());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_19 + getMchc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_20 + getPlt());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_21 + getW_scr());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_22 + getW_mcr());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_23 + getW_lcr());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_24 + getW_scc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_25 + getW_mcc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_26 + getW_lcc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_27 + getRdw_sd());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_28 + getRdw_cv());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_29 + getPdw());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_30 + getMpv());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_31 + getP_lcr());
		messageProperties.append(System.lineSeparator());
		messageProperties.append(MSG_PART_32 + getPct());

		return messageProperties.toString();
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getStx() {
		return stx;
	}

	public void setStx(String stx) {
		this.stx = stx;
	}

	public String getTextDistinctionCode1() {
		return textDistinctionCode1;
	}

	public void setTextDistinctionCode1(String textDistinctionCode1) {
		this.textDistinctionCode1 = textDistinctionCode1;
	}

	public String getTextDistinctionCode2() {
		return textDistinctionCode2;
	}

	public void setTextDistinctionCode2(String textDistinctionCode2) {
		this.textDistinctionCode2 = textDistinctionCode2;
	}

	public String getSampleDistinctionCode() {
		return sampleDistinctionCode;
	}

	public void setSampleDistinctionCode(String sampleDistinctionCode) {
		this.sampleDistinctionCode = sampleDistinctionCode;
	}

	public String getInstrumentID() {
		return instrumentID;
	}

	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getAnalysisStatus() {
		return analysisStatus;
	}

	public void setAnalysisStatus(String analysisStatus) {
		this.analysisStatus = analysisStatus;
	}

	public String getParticleSize() {
		return particleSize;
	}

	public void setParticleSize(String particleSize) {
		this.particleSize = particleSize;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getWbc() {
		// Float as_long = new Float(wbc);
		Float as_long = Float.parseFloat(wbc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setWbc(String wbc) {
		this.wbc = wbc;
	}

	public String getRbc() {
		Float as_long = Float.parseFloat(rbc);
		Float trimmed = as_long / 1000;
		return trimmed.toString();
	}

	public void setRbc(String rbc) {
		this.rbc = rbc;
	}

	public String getHgb() {
		Float as_long = Float.parseFloat(hgb);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setHgb(String hgb) {
		this.hgb = hgb;
	}

	public String getHct() {
		Float as_long = Float.parseFloat(hct);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setHct(String hct) {
		this.hct = hct;
	}

	public String getMcv() {
		Float as_long = Float.parseFloat(mcv);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setMcv(String mcv) {
		this.mcv = mcv;
	}

	public String getMch() {
		Float as_long = Float.parseFloat(mch);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setMch(String mch) {
		this.mch = mch;
	}

	public String getMchc() {
		Float as_long = Float.parseFloat(mchc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setMchc(String mchc) {
		this.mchc = mchc;
	}

	public String getPlt() {
		Float as_long = Float.parseFloat(plt);
		Float trimmed = as_long / 10;
		return trimmed.toString();
	}

	public void setPlt(String plt) {
		this.plt = plt;
	}

	public String getW_scr() {
		Float as_long = Float.parseFloat(w_scr);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_scr(String w_scr) {
		this.w_scr = w_scr;
	}

	public String getW_mcr() {
		Float as_long = Float.parseFloat(w_mcr);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_mcr(String w_mcr) {
		this.w_mcr = w_mcr;
	}

	public String getW_lcr() {
		Float as_long = Float.parseFloat(w_lcr);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_lcr(String w_lcr) {
		this.w_lcr = w_lcr;
	}

	public String getW_scc() {
		Float as_long = Float.parseFloat(w_scc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_scc(String w_scc) {
		this.w_scc = w_scc;
	}

	public String getW_mcc() {
		Float as_long = Float.parseFloat(w_mcc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_mcc(String w_mcc) {
		this.w_mcc = w_mcc;
	}

	public String getW_lcc() {
		Float as_long = Float.parseFloat(w_lcc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_lcc(String w_lcc) {
		this.w_lcc = w_lcc;
	}

	public String getRdw_sd() {
		Float as_long = Float.parseFloat(rdw_sd);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setRdw_sd(String rdw_sd) {
		this.rdw_sd = rdw_sd;
	}

	public String getRdw_cv() {
		Float as_long = Float.parseFloat(rdw_cv);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setRdw_cv(String rdw_cv) {
		this.rdw_cv = rdw_cv;
	}

	public String getPdw() {
		Float as_long = Float.parseFloat(pdw);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setPdw(String pdw) {
		this.pdw = pdw;
	}

	public String getMpv() {
		Float as_long = Float.parseFloat(mpv);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setMpv(String mpv) {
		this.mpv = mpv;
	}

	public String getP_lcr() {
		Float as_long = Float.parseFloat(p_lcr);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setP_lcr(String p_lcr) {
		this.p_lcr = p_lcr;
	}

	public String getPct() {
		Float as_long = Float.parseFloat(pct);
		Float trimmed = as_long / 1000;
		return trimmed.toString();
	}

	public void setPct(String pct) {
		this.pct = pct;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getSampleID() {
		return sampleID;
	}

	public void setSampleID(String sampleID) {
		this.sampleID = sampleID;
	}
}
