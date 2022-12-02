package ch.framsteg.hl7.sysmex.server.messages;

import org.apache.log4j.Logger;

public class XPMessage {

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
		logger.info("--");
		logger.info("Generate XPMessage");
		// Text Distinction Code1
		setTextDistinctionCode1(input.substring(0, 1));
		logger.info("Extract 'Text Distinction Code1' at (0,1): " + getTextDistinctionCode1());
		// Text Distinction Code2
		setTextDistinctionCode2(input.substring(1, 2));
		logger.info("Extract 'Text Distinction Code2' at (1,2): " + getTextDistinctionCode2());
		// Sample Distinction Code
		setSampleDistinctionCode(input.substring(2, 3));
		logger.info("Extract 'Sample Distinction Code' at (2,3): " + getSampleDistinctionCode());
		// Instrument ID
		setInstrumentID(input.substring(3, 43));
		logger.info("Extract 'Instrument ID' at (3,43): " + getInstrumentID());
		// Year
		setYear(input.substring(43, 47));
		logger.info("Extract 'Year' at (43,47): " + getYear());
		// Month
		setMonth(input.substring(47, 49));
		logger.info("Extract 'Month' at (47,49): " + getMonth());
		// Day
		setDay(input.substring(49, 51));
		logger.info("Extract 'Day' at (49,51): " + getDay());
		// Analysis Status
		setAnalysisStatus(input.substring(51, 52));
		logger.info("Extract 'Analysis Status' at (51,52): " + getAnalysisStatus());
		// Sample ID
		setSampleID(input.substring(52, 67));
		logger.info("Extract 'Sample ID' at (52,67): " + getSampleID());
		// Particle Size
		setParticleSize(input.substring(67, 73));
		logger.info("Extract 'Particle Size' at (67,73): " + getParticleSize());
		// Reserve
		setReserve(input.substring(73, 74));
		logger.info("Extract 'Reserve Bit' at (73,74): " + getReserve());
		// WBC
		setWbc(input.substring(74, 79));
		logger.info("Extract 'WBC' at (74,79): " + getWbc());
		// RBC
		setRbc(input.substring(79, 84));
		logger.info("Extract 'RBC' at (79,84): " + getRbc());
		// HGB
		setHgb(input.substring(84, 89));
		logger.info("Extract 'HGB' at (84,89): " + getHgb());
		// HCT
		setHct(input.substring(89, 94));
		logger.info("Extract 'HCT' at (89,94): " + getHct());
		// MCV
		setMcv(input.substring(94, 99));
		logger.info("Extract 'MCV' at (94,99): " + getMcv());
		// MCH
		setMch(input.substring(99, 104));
		logger.info("Extract 'MCH' at (99,104): " + getMch());
		// MCHC
		setMchc(input.substring(104, 109));
		logger.info("Extract 'MCHC' at (104,109): " + getMchc());
		// PLT
		setPlt(input.substring(109, 114));
		logger.info("Extract 'PLT' at (109,114): " + getPlt());
		// LYM%
		setW_scr(input.substring(114, 119));
		logger.info("Extract 'LYM%' at (114,119): " + getW_scr());
		// MXD%
		setW_mcr(input.substring(119, 124));
		logger.info("Extract 'MXD%' at (119,119): " + getW_mcr());
		// NEUT%
		setW_lcr(input.substring(124, 129));
		logger.info("Extract 'NEUT%' at (124,129): " + getW_lcr());
		// LYM#
		setW_scc(input.substring(129, 134));
		logger.info("Extract 'LYM#' at (129,134): " + getW_scc());
		// MXD#
		setW_mcc(input.substring(134, 139));
		logger.info("Extract 'MXD#' at (134,139): " + getW_mcc());
		// NEUT#
		setW_lcc(input.substring(139, 144));
		logger.info("Extract 'NEUT#' at (139,144): " + getW_lcc());
		// RDW-SD
		setRdw_sd(input.substring(144, 149));
		logger.info("Extract 'RDW-SD' at (144,149): " + getRdw_sd());
		// RDW-CV
		setRdw_cv(input.substring(149, 154));
		logger.info("Extract 'RDW-CV' at (149,154): " + getRdw_cv());
		// PDW
		setPdw(input.substring(154, 159));
		logger.info("Extract 'PDW' at (154,159): " + getPdw());
		// MPV
		setMpv(input.substring(159, 164));
		logger.info("Extract 'MPV' at (159,164): " + getMpv());
		// P-LCR
		setP_lcr(input.substring(164, 169));
		logger.info("Extract 'P-LCR' at (164,169): " + getP_lcr());
		// PCT
		setPct(input.substring(169, 174));
		logger.info("Extract 'PCT' at (169,174): " + getPct());
	}

	@Override
	public String toString() {
		StringBuilder messageProperties = new StringBuilder();
		messageProperties.append("STX: " + getStx());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Text Distinction Code 1: " + getTextDistinctionCode1());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Text Distinction Code 2: " + getTextDistinctionCode2());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Sample Distinction Code: " + getSampleDistinctionCode());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Instrument ID: " + getInstrumentID());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Year: " + getYear());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Month: " + getMonth());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Day: " + getDay());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Analysis Status: " + getAnalysisStatus());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Sample ID: " + getSampleID());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Particle Size: " + getParticleSize());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("Reserve: " + getReserve());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("WBC: " + getWbc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("RBC: " + getRbc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("HGB: " + getHgb());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("HCT: " + getHct());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("MCV: " + getMcv());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("MCH: " + getMch());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("MCHC: " + getMchc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("PLT: " + getPlt());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("W SCR (LYM%): " + getW_scr());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("W MCR (MXD%): " + getW_mcr());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("W LCR (NEUT%): " + getW_lcr());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("W SCC (LYM#): " + getW_scc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("W MCC (MXD#): " + getW_mcc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("W LCC (NEUT#): " + getW_lcc());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("RDW_SD: " + getRdw_sd());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("RDW_CV " + getRdw_cv());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("PDW: " + getPdw());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("MPV: " + getMpv());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("P_LCR: " + getP_lcr());
		messageProperties.append(System.lineSeparator());
		messageProperties.append("PCT: " + getPct());

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
		Float as_long = new Float(wbc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setWbc(String wbc) {
		this.wbc = wbc;
	}

	public String getRbc() {
		Float as_long = new Float(rbc);
		Float trimmed = as_long / 1000;
		return trimmed.toString();
	}

	public void setRbc(String rbc) {
		this.rbc = rbc;
	}

	public String getHgb() {
		Float as_long = new Float(hgb);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setHgb(String hgb) {
		this.hgb = hgb;
	}

	public String getHct() {
		Float as_long = new Float(hct);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setHct(String hct) {
		this.hct = hct;
	}

	public String getMcv() {
		Float as_long = new Float(mcv);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setMcv(String mcv) {
		this.mcv = mcv;
	}

	public String getMch() {
		Float as_long = new Float(mch);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setMch(String mch) {
		this.mch = mch;
	}

	public String getMchc() {
		Float as_long = new Float(mchc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setMchc(String mchc) {
		this.mchc = mchc;
	}

	public String getPlt() {
		Float as_long = new Float(plt);
		Float trimmed = as_long / 10;
		return trimmed.toString();
	}

	public void setPlt(String plt) {
		this.plt = plt;
	}

	public String getW_scr() {
		Float as_long = new Float(w_scr);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_scr(String w_scr) {
		this.w_scr = w_scr;
	}

	public String getW_mcr() {
		Float as_long = new Float(w_mcr);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_mcr(String w_mcr) {
		this.w_mcr = w_mcr;
	}

	public String getW_lcr() {
		Float as_long = new Float(w_lcr);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_lcr(String w_lcr) {
		this.w_lcr = w_lcr;
	}

	public String getW_scc() {
		Float as_long = new Float(w_scc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_scc(String w_scc) {
		this.w_scc = w_scc;
	}

	public String getW_mcc() {
		Float as_long = new Float(w_mcc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_mcc(String w_mcc) {
		this.w_mcc = w_mcc;
	}

	public String getW_lcc() {
		Float as_long = new Float(w_lcc);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setW_lcc(String w_lcc) {
		this.w_lcc = w_lcc;
	}

	public String getRdw_sd() {
		Float as_long = new Float(rdw_sd);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setRdw_sd(String rdw_sd) {
		this.rdw_sd = rdw_sd;
	}

	public String getRdw_cv() {
		Float as_long = new Float(rdw_cv);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setRdw_cv(String rdw_cv) {
		this.rdw_cv = rdw_cv;
	}

	public String getPdw() {
		Float as_long = new Float(pdw);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setPdw(String pdw) {
		this.pdw = pdw;
	}

	public String getMpv() {
		Float as_long = new Float(mpv);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setMpv(String mpv) {
		this.mpv = mpv;
	}

	public String getP_lcr() {
		Float as_long = new Float(p_lcr);
		Float trimmed = as_long / 100;
		return trimmed.toString();
	}

	public void setP_lcr(String p_lcr) {
		this.p_lcr = p_lcr;
	}

	public String getPct() {
		Float as_long = new Float(pct);
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

