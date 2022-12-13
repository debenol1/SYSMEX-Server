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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/* Representing the HL7 message */
public class HL7Message {

	private final static String DATE_FORMAT = "yyyyMMddHHmmss";
	private final static String LOG_GETTING_TIMESTAMP = "Getting Timestamp: ";
	private final static String LOG_CREATING_MSG_SEG = "Creating MSG Segment: ";
	private final static String LOG_CREATING_PID_SEG = "Creating PID Segment: ";
	private final static String LOG_CREATING_PV1_SEG = "Creating PV1 Segment: ";
	private final static String LOG_CREATING_ORC_SEG = "Creating ORC Segment: ";
	private final static String LOG_CREATING_OBR_SEG = "Creating OBR Segment: ";
	private final static String LOG_CREATING_WBC_SEG = "Creating WBC Segment: ";
	private final static String LOG_CREATING_RBC_SEG = "Creating RBC Segment: ";
	private final static String LOG_CREATING_HBG_SEG = "Creating HBG Segment: ";
	private final static String LOG_CREATING_HCT_SEG = "Creating HCT Segment: ";
	private final static String LOG_CREATING_MCV_SEG = "Creating MCV Segment: ";
	private final static String LOG_CREATING_MCH_SEG = "Creating MCH Segment: ";
	private final static String LOG_CREATING_MCHC_SEG = "Creating MCHC Segment: ";
	private final static String LOG_CREATING_PLT_SEG = "Creating PLT Segment: ";
	private final static String LOG_CREATING_ALYM_SEG = "Creating ALYM Segment: ";
	private final static String LOG_CREATING_MXD_SEG = "Creating ALYM Segment: ";
	private final static String LOG_CREATING_ANEUT_SEG = "Creating ANEUT Segment: ";
	private final static String LOG_CREATING_ALYMA_SEG = "Creating ALYMA Segment: ";
	private final static String LOG_CREATING_MXDA_SEG = "Creating MXDA Segment: ";
	private final static String LOG_CREATING_ANEUTA_SEG = "Creating ANEUTA Segment: ";
	private final static String LOG_CREATING_RDW_SD_SEG = "Creating RDW-SD Segment: ";
	private final static String LOG_CREATING_RDW_CV_SEG = "Creating RDW-CV Segment: ";
	private final static String LOG_CREATING_PDW_SEG = "Creating PDW Segment: ";
	private final static String LOG_CREATING_MPV_SEG = "Creating MPV Segment: ";
	private final static String LOG_CREATING_P_LCR_SEG = "Creating P-LCR Segment: ";
	private final static String LOG_CREATING_PCT_SEG = "Creating PCT Segment: ";

	private final static String LOG_EXTRACTED_ID = "Extracted ID: ";
	private final static String LOG_DB_CONN_ESTABLISHED = "Database connection established: ";
	private final static String LOG_DB_STMT_CREATED = "Statement created: ";

	private final static String PROP_HL7_MSH = "hl7.msh";
	private final static String PROP_HL7_PID = "hl7.pid";
	private final static String PROP_HL7_PV1 = "hl7.pv1";
	private final static String PROP_HL7_ORC = "hl7.orc";
	private final static String PROP_HL7_OBR = "hl7.obr";
	private final static String PROP_HL7_OBX_NM_WBC = "hl7.obx.nm.wbc";
	private final static String PROP_HL7_OBX_NM_WBC_UNIT = "hl7.obx.nm.wbc.unit";
	private final static String PROP_HL7_OBX_NM_WBC_REF = "hl7.obx.nm.wbc.ref";
	private final static String PROP_HL7_OBX_NM_RBC = "hl7.obx.nm.rbc";
	private final static String PROP_HL7_OBX_NM_RBC_UNIT = "hl7.obx.nm.rbc.unit";
	private final static String PROP_HL7_OBX_NM_RBC_REF = "hl7.obx.nm.rbc.ref";
	private final static String PROP_HL7_OBX_NM_HBG = "hl7.obx.nm.hbg";
	private final static String PROP_HL7_OBX_NM_HBG_UNIT = "hl7.obx.nm.hbg.unit";
	private final static String PROP_HL7_OBX_NM_HBG_REF = "hl7.obx.nm.hbg.ref";
	private final static String PROP_HL7_OBX_NM_HCT = "hl7.obx.nm.hct";
	private final static String PROP_HL7_OBX_NM_HCT_UNIT = "hl7.obx.nm.hct.unit";
	private final static String PROP_HL7_OBX_NM_HCT_REF = "hl7.obx.nm.hct.ref";
	private final static String PROP_HL7_OBX_NM_MCV = "hl7.obx.nm.mcv";
	private final static String PROP_HL7_OBX_NM_MCV_UNIT = "hl7.obx.nm.mcv.unit";
	private final static String PROP_HL7_OBX_NM_MCV_REF = "hl7.obx.nm.mcv.ref";
	private final static String PROP_HL7_OBX_NM_MCH = "hl7.obx.nm.mch";
	private final static String PROP_HL7_OBX_NM_MCH_UNIT = "hl7.obx.nm.mch.unit";
	private final static String PROP_HL7_OBX_NM_MCH_REF = "hl7.obx.nm.mch.ref";
	private final static String PROP_HL7_OBX_NM_MCHC = "hl7.obx.nm.mchc";
	private final static String PROP_HL7_OBX_NM_MCHC_UNIT = "hl7.obx.nm.mchc.unit";
	private final static String PROP_HL7_OBX_NM_MCHC_REF = "hl7.obx.nm.mchc.ref";
	private final static String PROP_HL7_OBX_NM_PLT = "hl7.obx.nm.plt";
	private final static String PROP_HL7_OBX_NM_PLT_UNIT = "hl7.obx.nm.plt.unit";
	private final static String PROP_HL7_OBX_NM_PLT_REF = "hl7.obx.nm.plt.ref";
	private final static String PROP_HL7_OBX_NM_ALYM = "hl7.obx.nm.alym";
	private final static String PROP_HL7_OBX_NM_ALYM_UNIT = "hl7.obx.nm.alym.unit";
	private final static String PROP_HL7_OBX_NM_ALYM_REF = "hl7.obx.nm.alym.ref";
	private final static String PROP_HL7_OBX_NM_MXD = "hl7.obx.nm.mxd";
	private final static String PROP_HL7_OBX_NM_MXD_UNIT = "hl7.obx.nm.mxd.unit";
	private final static String PROP_HL7_OBX_NM_MXD_REF = "hl7.obx.nm.mxd.ref";
	private final static String PROP_HL7_OBX_NM_ANEUT = "hl7.obx.nm.aneut";
	private final static String PROP_HL7_OBX_NM_ANEUT_UNIT = "hl7.obx.nm.aneut.unit";
	private final static String PROP_HL7_OBX_NM_ANEUT_REF = "hl7.obx.nm.aneut.ref";
	private final static String PROP_HL7_OBX_NM_ALYMA = "hl7.obx.nm.alyma";
	private final static String PROP_HL7_OBX_NM_ALYMA_UNIT = "hl7.obx.nm.alyma.unit";
	private final static String PROP_HL7_OBX_NM_ALYMA_REF = "hl7.obx.nm.alyma.ref";
	private final static String PROP_HL7_OBX_NM_MXDA = "hl7.obx.nm.mxda";
	private final static String PROP_HL7_OBX_NM_MXDA_UNIT = "hl7.obx.nm.mxda.unit";
	private final static String PROP_HL7_OBX_NM_MXDA_REF = "hl7.obx.nm.mxda.ref";
	private final static String PROP_HL7_OBX_NM_ANEUTA = "hl7.obx.nm.aneuta";
	private final static String PROP_HL7_OBX_NM_ANEUTA_UNIT = "hl7.obx.nm.aneuta.unit";
	private final static String PROP_HL7_OBX_NM_ANEUTA_REF = "hl7.obx.nm.aneuta.ref";
	private final static String PROP_HL7_OBX_NM_RDW_SD = "hl7.obx.nm.rdw-sd";
	private final static String PROP_HL7_OBX_NM_RDW_SD_UNIT = "hl7.obx.nm.rdw-sd.unit";
	private final static String PROP_HL7_OBX_NM_RDW_SD_REF = "hl7.obx.nm.rdw-sd.ref";
	private final static String PROP_HL7_OBX_NM_RDW_CV = "hl7.obx.nm.rdw-cv";
	private final static String PROP_HL7_OBX_NM_RDW_CV_UNIT = "hl7.obx.nm.rdw-cv.unit";
	private final static String PROP_HL7_OBX_NM_RDW_CV_REF = "hl7.obx.nm.rdw-cv.ref";
	private final static String PROP_HL7_OBX_NM_PDW = "hl7.obx.nm.pdw";
	private final static String PROP_HL7_OBX_NM_PDW_UNIT = "hl7.obx.nm.pdw.unit";
	private final static String PROP_HL7_OBX_NM_PDW_REF = "hl7.obx.nm.pdw.ref";
	private final static String PROP_HL7_OBX_NM_MPV = "hl7.obx.nm.mpv";
	private final static String PROP_HL7_OBX_NM_MPV_UNIT = "hl7.obx.nm.mpv.unit";
	private final static String PROP_HL7_OBX_NM_MPV_REF = "hl7.obx.nm.mpv.ref";
	private final static String PROP_HL7_OBX_NM_P_LCR = "hl7.obx.nm.p-lcr";
	private final static String PROP_HL7_OBX_NM_P_LCR_UNIT = "hl7.obx.nm.p-lcr.unit";
	private final static String PROP_HL7_OBX_NM_P_LCR_REF = "hl7.obx.nm.p-lcr.ref";
	private final static String PROP_HL7_OBX_NM_PCT = "hl7.obx.nm.pct";
	private final static String PROP_HL7_OBX_NM_PCT_UNIT = "hl7.obx.nm.pct.unit";
	private final static String PROP_HL7_OBX_NM_PCT_REF = "hl7.obx.nm.pct.ref";

	private final static String SQL_STRING = "SELECT bezeichnung2,bezeichnung1,geburtsdatum FROM kontakt WHERE patientnr ='";

	private final static String NEWLINE = "\\n";
	private final static String QUOTE = "'";

	private final static String JDBC_URL = "url";
	private final static String JDBC_USER = "user";
	private final static String JDBC_PW = "password";
	private final static String HL7_SEG_PATTERN = "-?\\d+(\\.\\d+)?";
	private final static String AUTOM_ASSIGNEMENT = "automatic.patient.assignment";
	private final static String TRUE = "true";
	private final static String DELIM = ", ";
	private final static String PAT_ID = "Patient ID ";
	private final static String IS_RESOLVED = " is resolved: ";
	private final static String IDENTIFIED_NAME = "Identified name: ";
	private final static String IDENTIFIED_BIRTHDAY = "Identified birthday: ";
	private final static String UNRESOLVED_PAT_1 = "Message/Patient with ID (";
	private final static String UNRESOLVED_PAT_2 = ") could not be resolved";
	private final static String NO_NUMERIC_ID_1 = "No numeric ID (";
	private final static String NO_NUMERIC_ID_2 = "). Message will be skipped";

	private Properties properties;
	private XPMessage xpMessage;
	private StringBuilder message;

	private String id;
	private String name;
	private String birthdate;

	private boolean resolved;

	private static Logger logger = Logger.getLogger(HL7Message.class);

	public HL7Message(Properties properties, XPMessage xpMessage) {
		this.properties = properties;
		this.xpMessage = xpMessage;
		identify(xpMessage.getSampleID());
	}

	public void build() {
		String systemTime = new SimpleDateFormat(DATE_FORMAT).format(new Date(System.currentTimeMillis()));
		logger.info(LOG_GETTING_TIMESTAMP + systemTime);
		String msg_segment = new String();
		String pid_segment = new String();
		String pv1_segment = new String();
		String orc_segment = new String();
		String obr_segment = new String();
		String obx_wbc_segment = new String();
		String obx_rbc_segment = new String();
		String obx_hbg_segment = new String();
		String obx_hct_segment = new String();
		String obx_mcv_segment = new String();
		String obx_mch_segment = new String();
		String obx_mchc_segment = new String();
		String obx_plt_segment = new String();
		String obx_alym_segment = new String();
		String obx_mxd_segment = new String();
		String obx_aneut_segment = new String();
		String obx_alyma_segment = new String();
		String obx_mxda_segment = new String();
		String obx_aneuta_segment = new String();
		String obx_rdw_sd_segment = new String();
		String obx_rdw_cv_segment = new String();
		String obx_pdw_segment = new String();
		String obx_mpv_segment = new String();
		String obx_p_lcr_segment = new String();
		String obx_pct_segment = new String();

		msg_segment = MessageFormat.format(properties.getProperty(PROP_HL7_MSH), systemTime);
		logger.info(LOG_CREATING_MSG_SEG + msg_segment);
		pid_segment = MessageFormat.format(properties.getProperty(PROP_HL7_PID), name, birthdate);
		logger.info(LOG_CREATING_PID_SEG + pid_segment);
		pv1_segment = properties.getProperty(PROP_HL7_PV1);
		logger.info(LOG_CREATING_PV1_SEG + pv1_segment);
		orc_segment = properties.getProperty(PROP_HL7_ORC);
		logger.info(LOG_CREATING_ORC_SEG + orc_segment);
		obr_segment = properties.getProperty(PROP_HL7_OBR);
		logger.info(LOG_CREATING_OBR_SEG + obr_segment);
		obx_wbc_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_WBC), xpMessage.getWbc(),
				properties.getProperty(PROP_HL7_OBX_NM_WBC_UNIT), properties.getProperty(PROP_HL7_OBX_NM_WBC_REF),
				systemTime);
		logger.info(LOG_CREATING_WBC_SEG + obx_wbc_segment);
		obx_rbc_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_RBC), xpMessage.getRbc(),
				properties.getProperty(PROP_HL7_OBX_NM_RBC_UNIT), properties.getProperty(PROP_HL7_OBX_NM_RBC_REF),
				systemTime);
		logger.info(LOG_CREATING_RBC_SEG + obx_rbc_segment);
		obx_hbg_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_HBG), xpMessage.getHgb(),
				properties.getProperty(PROP_HL7_OBX_NM_HBG_UNIT), properties.getProperty(PROP_HL7_OBX_NM_HBG_REF),
				systemTime);
		logger.info(LOG_CREATING_HBG_SEG + obx_hbg_segment);
		obx_hct_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_HCT), xpMessage.getHct(),
				properties.getProperty(PROP_HL7_OBX_NM_HCT_UNIT), properties.getProperty(PROP_HL7_OBX_NM_HCT_REF),
				systemTime);
		logger.info(LOG_CREATING_HCT_SEG + obx_hct_segment);
		obx_mcv_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_MCV), xpMessage.getMcv(),
				properties.getProperty(PROP_HL7_OBX_NM_MCV_UNIT), properties.getProperty(PROP_HL7_OBX_NM_MCV_REF),
				systemTime);
		logger.info(LOG_CREATING_MCV_SEG + obx_mcv_segment);
		obx_mch_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_MCH), xpMessage.getMch(),
				properties.getProperty(PROP_HL7_OBX_NM_MCH_UNIT), properties.getProperty(PROP_HL7_OBX_NM_MCH_REF),
				systemTime);
		logger.info(LOG_CREATING_MCH_SEG + obx_mch_segment);
		obx_mchc_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_MCHC), xpMessage.getMchc(),
				properties.getProperty(PROP_HL7_OBX_NM_MCHC_UNIT), properties.getProperty(PROP_HL7_OBX_NM_MCHC_REF),
				systemTime);
		logger.info(LOG_CREATING_MCHC_SEG + obx_mchc_segment);
		obx_plt_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_PLT), xpMessage.getPlt(),
				properties.getProperty(PROP_HL7_OBX_NM_PLT_UNIT), properties.getProperty(PROP_HL7_OBX_NM_PLT_REF),
				systemTime);
		logger.info(LOG_CREATING_PLT_SEG + obx_plt_segment);
		obx_alym_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_ALYM), xpMessage.getW_scr(),
				properties.getProperty(PROP_HL7_OBX_NM_ALYM_UNIT), properties.getProperty(PROP_HL7_OBX_NM_ALYM_REF),
				systemTime);
		logger.info(LOG_CREATING_ALYM_SEG + obx_alym_segment);
		obx_mxd_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_MXD), xpMessage.getW_mcr(),
				properties.getProperty(PROP_HL7_OBX_NM_MXD_UNIT), properties.getProperty(PROP_HL7_OBX_NM_MXD_REF),
				systemTime);
		logger.info(LOG_CREATING_MXD_SEG + obx_mxd_segment);
		obx_aneut_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_ANEUT), xpMessage.getW_lcr(),
				properties.getProperty(PROP_HL7_OBX_NM_ANEUT_UNIT), properties.getProperty(PROP_HL7_OBX_NM_ANEUT_REF),
				systemTime);
		logger.info(LOG_CREATING_ANEUT_SEG + obx_aneut_segment);
		obx_alyma_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_ALYMA), xpMessage.getW_scc(),
				properties.getProperty(PROP_HL7_OBX_NM_ALYMA_UNIT), properties.getProperty(PROP_HL7_OBX_NM_ALYMA_REF),
				systemTime);
		logger.info(LOG_CREATING_ALYMA_SEG + obx_alyma_segment);
		obx_mxda_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_MXDA), xpMessage.getW_mcc(),
				properties.getProperty(PROP_HL7_OBX_NM_MXDA_UNIT), properties.getProperty(PROP_HL7_OBX_NM_MXDA_REF),
				systemTime);
		logger.info(LOG_CREATING_MXDA_SEG + obx_mxda_segment);
		obx_aneuta_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_ANEUTA), xpMessage.getW_lcc(),
				properties.getProperty(PROP_HL7_OBX_NM_ANEUTA_UNIT), properties.getProperty(PROP_HL7_OBX_NM_ANEUTA_REF),
				systemTime);
		logger.info(LOG_CREATING_ANEUTA_SEG + obx_aneuta_segment);
		obx_rdw_sd_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_RDW_SD), xpMessage.getRdw_sd(),
				properties.getProperty(PROP_HL7_OBX_NM_RDW_SD_UNIT), properties.getProperty(PROP_HL7_OBX_NM_RDW_SD_REF),
				systemTime);
		logger.info(LOG_CREATING_RDW_SD_SEG + obx_rdw_sd_segment);
		obx_rdw_cv_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_RDW_CV), xpMessage.getRdw_cv(),
				properties.getProperty(PROP_HL7_OBX_NM_RDW_CV_UNIT), properties.getProperty(PROP_HL7_OBX_NM_RDW_CV_REF),
				systemTime);
		logger.info(LOG_CREATING_RDW_CV_SEG + obx_rdw_cv_segment);
		obx_pdw_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_PDW), xpMessage.getPdw(),
				properties.getProperty(PROP_HL7_OBX_NM_PDW_UNIT), properties.getProperty(PROP_HL7_OBX_NM_PDW_REF),
				systemTime);
		logger.info(LOG_CREATING_PDW_SEG + obx_pdw_segment);
		obx_mpv_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_MPV), xpMessage.getMpv(),
				properties.getProperty(PROP_HL7_OBX_NM_MPV_UNIT), properties.getProperty(PROP_HL7_OBX_NM_MPV_REF),
				systemTime);
		logger.info(LOG_CREATING_MPV_SEG + obx_mpv_segment);
		obx_p_lcr_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_P_LCR), xpMessage.getP_lcr(),
				properties.getProperty(PROP_HL7_OBX_NM_P_LCR_UNIT), properties.getProperty(PROP_HL7_OBX_NM_P_LCR_REF),
				systemTime);
		logger.info(LOG_CREATING_P_LCR_SEG + obx_p_lcr_segment);
		obx_pct_segment = MessageFormat.format(properties.getProperty(PROP_HL7_OBX_NM_PCT), xpMessage.getPct(),
				properties.getProperty(PROP_HL7_OBX_NM_PCT_UNIT), properties.getProperty(PROP_HL7_OBX_NM_PCT_REF),
				systemTime);
		logger.info(LOG_CREATING_PCT_SEG + obx_pct_segment);

		message = new StringBuilder();
		message.append(msg_segment);
		message.append(NEWLINE);
		message.append(pid_segment);
		message.append(NEWLINE);
		message.append(pv1_segment);
		message.append(NEWLINE);
		message.append(orc_segment);
		message.append(NEWLINE);
		message.append(obr_segment);
		message.append(NEWLINE);
		message.append(obx_wbc_segment);
		message.append(NEWLINE);
		message.append(obx_rbc_segment);
		message.append(NEWLINE);
		message.append(obx_hbg_segment);
		message.append(NEWLINE);
		message.append(obx_hct_segment);
		message.append(NEWLINE);
		message.append(obx_mcv_segment);
		message.append(NEWLINE);
		message.append(obx_mch_segment);
		message.append(NEWLINE);
		message.append(obx_mchc_segment);
		message.append(NEWLINE);
		message.append(obx_plt_segment);
		message.append(NEWLINE);
		message.append(obx_alym_segment);
		message.append(NEWLINE);
		message.append(obx_mxd_segment);
		message.append(NEWLINE);
		message.append(obx_aneut_segment);
		message.append(NEWLINE);
		message.append(obx_alyma_segment);
		message.append(NEWLINE);
		message.append(obx_mxda_segment);
		message.append(NEWLINE);
		message.append(obx_aneuta_segment);
		message.append(NEWLINE);
		message.append(obx_rdw_sd_segment);
		message.append(NEWLINE);
		message.append(obx_rdw_cv_segment);
		message.append(NEWLINE);
		message.append(obx_pdw_segment);
		message.append(NEWLINE);
		message.append(obx_mpv_segment);
		message.append(NEWLINE);
		message.append(obx_p_lcr_segment);
		message.append(NEWLINE);
		message.append(obx_pct_segment);
	}

	@Override
	public String toString() {
		return message.toString();
	}

	/* Identifying the patient's name and firstname */
	public void identify(String id) {

		String url = properties.getProperty(JDBC_URL);
		String user = properties.getProperty(JDBC_USER);
		String password = properties.getProperty(JDBC_PW);
		setResolved(false);

		Pattern pattern = Pattern.compile(HL7_SEG_PATTERN);

		if (!id.isEmpty() && pattern.matcher(id).matches()) {
			String trimmedId = trim(id);
			logger.info(LOG_EXTRACTED_ID + trimmedId);
			setId(trimmedId);
			try {
				Connection conn = DriverManager.getConnection(url, user, password);
				logger.info(LOG_DB_CONN_ESTABLISHED + QUOTE + conn.isValid(0) + QUOTE);
				Statement st = conn.createStatement();
				logger.info(LOG_DB_STMT_CREATED + QUOTE + !st.isClosed() + QUOTE);
				ResultSet rs = st.executeQuery(SQL_STRING + trimmedId + QUOTE);
				while (rs.next()) {
					// Setting automatic.patient.assignment mode on/off
					// by switching prename, name to name, prename. Elexis the
					// either interrupts or automatically starts the import
					if (properties.getProperty(AUTOM_ASSIGNEMENT).equalsIgnoreCase(TRUE)) {
						name = rs.getString(2) + DELIM + rs.getString(1);
					} else {
						name = rs.getString(1) + DELIM + rs.getString(2);
					}
					birthdate = rs.getString(3);
					setResolved(!rs.getString(1).isEmpty() && !rs.getString(2).isEmpty() && !rs.getString(3).isEmpty());

					logger.info(PAT_ID + id + IS_RESOLVED + resolved);
					logger.info(AUTOM_ASSIGNEMENT + QUOTE + properties.getProperty(AUTOM_ASSIGNEMENT) + QUOTE);
					logger.info(IDENTIFIED_NAME + QUOTE + name + QUOTE);
					logger.info(IDENTIFIED_BIRTHDAY + QUOTE + birthdate + QUOTE);
				}
				if (!isResolved()) {
					logger.info(UNRESOLVED_PAT_1 + getId() + UNRESOLVED_PAT_2);
				}

				rs.close();
				st.close();

			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Error", e);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			setId(id);
			logger.info(NO_NUMERIC_ID_1 + getId() + NO_NUMERIC_ID_2);
		}
	}

	private String trim(String id) {
		int i = 0;
		for (char c : id.toCharArray()) {
			if (c != '0') {
				break;
			}
			i++;
		}
		String result = id.substring(i, id.length());
		return result;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
