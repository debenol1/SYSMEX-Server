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

public class HL7Message {

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
		String systemTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
		logger.info("Getting Timestamp: " + systemTime);
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

		msg_segment = MessageFormat.format(properties.getProperty("hl7.msh"), systemTime);
		logger.info("Creating MSG Segment: " + msg_segment);
		pid_segment = MessageFormat.format(properties.getProperty("hl7.pid"), name, birthdate);
		logger.info("Creating PID Segment: " + pid_segment);
		pv1_segment = properties.getProperty("hl7.pv1");
		logger.info("Creating PV1 Segment: " + pv1_segment);
		orc_segment = properties.getProperty("hl7.orc");
		logger.info("Creating ORC Segment: " + orc_segment);
		obr_segment = properties.getProperty("hl7.obr");
		logger.info("Creating OBR Segment: " + obr_segment);
		obx_wbc_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.wbc"), xpMessage.getWbc(),
				properties.getProperty("hl7.obx.nm.wbc.unit"), properties.getProperty("hl7.obx.nm.wbc.ref"),
				systemTime);
		logger.info("Creating WBC Segment: " + obx_wbc_segment);
		obx_rbc_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.rbc"), xpMessage.getRbc(),
				properties.getProperty("hl7.obx.nm.rbc.unit"), properties.getProperty("hl7.obx.nm.rbc.ref"),
				systemTime);
		logger.info("Creating RBC Segment: " + obx_rbc_segment);
		obx_hbg_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.hbg"), xpMessage.getHgb(),
				properties.getProperty("hl7.obx.nm.hbg.unit"), properties.getProperty("hl7.obx.nm.hbg.ref"),
				systemTime);
		logger.info("Creating HBG Segment: " + obx_hbg_segment);
		obx_hct_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.hct"), xpMessage.getHct(),
				properties.getProperty("hl7.obx.nm.hct.unit"), properties.getProperty("hl7.obx.nm.hct.ref"),
				systemTime);
		logger.info("Creating HCT Segment: " + obx_hct_segment);
		obx_mcv_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.mcv"), xpMessage.getMcv(),
				properties.getProperty("hl7.obx.nm.mcv.unit"), properties.getProperty("hl7.obx.nm.mcv.ref"),
				systemTime);
		logger.info("Creating MCV Segment: " + obx_mcv_segment);
		obx_mch_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.mch"), xpMessage.getMch(),
				properties.getProperty("hl7.obx.nm.mch.unit"), properties.getProperty("hl7.obx.nm.mch.ref"),
				systemTime);
		logger.info("Creating MCH Segment: " + obx_mch_segment);
		obx_mchc_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.mchc"), xpMessage.getMchc(),
				properties.getProperty("hl7.obx.nm.mchc.unit"), properties.getProperty("hl7.obx.nm.mchc.ref"),
				systemTime);
		logger.info("Creating MCHC Segment: " + obx_mchc_segment);
		obx_plt_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.plt"), xpMessage.getPlt(),
				properties.getProperty("hl7.obx.nm.plt.unit"), properties.getProperty("hl7.obx.nm.plt.ref"),
				systemTime);
		logger.info("Creating PLT Segment: " + obx_plt_segment);
		obx_alym_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.alym"), xpMessage.getW_scr(),
				properties.getProperty("hl7.obx.nm.alym.unit"), properties.getProperty("hl7.obx.nm.alym.ref"),
				systemTime);
		logger.info("Creating ALYM Segment: " + obx_alym_segment);
		obx_mxd_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.mxd"), xpMessage.getW_mcr(),
				properties.getProperty("hl7.obx.nm.mxd.unit"), properties.getProperty("hl7.obx.nm.mxd.ref"),
				systemTime);
		logger.info("Creating MXD Segment: " + obx_mxd_segment);
		obx_aneut_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.aneut"), xpMessage.getW_lcr(),
				properties.getProperty("hl7.obx.nm.aneut.unit"), properties.getProperty("hl7.obx.nm.aneut.ref"),
				systemTime);
		logger.info("Creating ANEUT Segment: " + obx_aneut_segment);
		obx_alyma_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.alyma"), xpMessage.getW_scc(),
				properties.getProperty("hl7.obx.nm.alyma.unit"), properties.getProperty("hl7.obx.nm.alyma.ref"),
				systemTime);
		logger.info("Creating ALYMA Segment: " + obx_alyma_segment);
		obx_mxda_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.mxda"), xpMessage.getW_mcc(),
				properties.getProperty("hl7.obx.nm.mxda.unit"), properties.getProperty("hl7.obx.nm.mxda.ref"),
				systemTime);
		logger.info("Creating MXDA Segment: " + obx_mxda_segment);
		obx_aneuta_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.aneuta"), xpMessage.getW_lcc(),
				properties.getProperty("hl7.obx.nm.aneuta.unit"), properties.getProperty("hl7.obx.nm.aneuta.ref"),
				systemTime);
		logger.info("Creating ANEUTA Segment: " + obx_aneuta_segment);
		obx_rdw_sd_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.rdw-sd"), xpMessage.getRdw_sd(),
				properties.getProperty("hl7.obx.nm.rdw-sd.unit"), properties.getProperty("hl7.obx.nm.rdw-sd.ref"),
				systemTime);
		logger.info("Creating RDW-SD Segment: " + obx_rdw_sd_segment);
		obx_rdw_cv_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.rdw-cv"), xpMessage.getRdw_cv(),
				properties.getProperty("hl7.obx.nm.rdw-cv.unit"), properties.getProperty("hl7.obx.nm.rdw-cv.ref"),
				systemTime);
		logger.info("Creating RDW-CV Segment: " + obx_rdw_cv_segment);
		obx_pdw_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.pdw"), xpMessage.getPdw(),
				properties.getProperty("hl7.obx.nm.pdw.unit"), properties.getProperty("hl7.obx.nm.pdw.ref"),
				systemTime);
		logger.info("Creating PDW Segment: " + obx_pdw_segment);
		obx_mpv_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.mpv"), xpMessage.getMpv(),
				properties.getProperty("hl7.obx.nm.mpv.unit"), properties.getProperty("hl7.obx.nm.mpv.ref"),
				systemTime);
		logger.info("Creating MPV Segment: " + obx_mpv_segment);
		obx_p_lcr_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.p-lcr"), xpMessage.getP_lcr(),
				properties.getProperty("hl7.obx.nm.p-lcr.unit"), properties.getProperty("hl7.obx.nm.p-lcr.ref"),
				systemTime);
		logger.info("Creating P-LCR Segment: " + obx_p_lcr_segment);
		obx_pct_segment = MessageFormat.format(properties.getProperty("hl7.obx.nm.pct"), xpMessage.getPct(),
				properties.getProperty("hl7.obx.nm.pct.unit"), properties.getProperty("hl7.obx.nm.pct.ref"),
				systemTime);
		logger.info("Creating PCT Segment: " + obx_pct_segment);
		
		message = new StringBuilder();
		message.append(msg_segment);
		message.append("\n");
		message.append(pid_segment);
		message.append("\n");
		message.append(pv1_segment);
		message.append("\n");
		message.append(orc_segment);
		message.append("\n");
		message.append(obr_segment);
		message.append("\n");
		message.append(obx_wbc_segment);
		message.append("\n");
		message.append(obx_rbc_segment);
		message.append("\n");
		message.append(obx_hbg_segment);
		message.append("\n");
		message.append(obx_hct_segment);
		message.append("\n");
		message.append(obx_mcv_segment);
		message.append("\n");
		message.append(obx_mch_segment);
		message.append("\n");
		message.append(obx_mchc_segment);
		message.append("\n");
		message.append(obx_plt_segment);
		message.append("\n");
		message.append(obx_alym_segment);
		message.append("\n");
		message.append(obx_mxd_segment);
		message.append("\n");
		message.append(obx_aneut_segment);
		message.append("\n");
		message.append(obx_alyma_segment);
		message.append("\n");
		message.append(obx_mxda_segment);
		message.append("\n");
		message.append(obx_aneuta_segment);
		message.append("\n");
		message.append(obx_rdw_sd_segment);
		message.append("\n");
		message.append(obx_rdw_cv_segment);
		message.append("\n");
		message.append(obx_pdw_segment);
		message.append("\n");
		message.append(obx_mpv_segment);
		message.append("\n");
		message.append(obx_p_lcr_segment);
		message.append("\n");
		message.append(obx_pct_segment);

	}

	@Override
	public String toString() {
		return message.toString();
	}

	public void identify(String id) {

		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		setResolved(false);

		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

		if (!id.isEmpty() && pattern.matcher(id).matches()) {
			//String trimmedId = id.replaceAll("0", "");
			String trimmedId = trim(id);
			logger.info("Extracted ID: " + trimmedId);
			setId(trimmedId);
			try {
				Connection conn = DriverManager.getConnection(url, user, password);
				logger.info("Database connection established: " + "\'" + conn.isValid(0) + "'");
				Statement st = conn.createStatement();
				logger.info("Statement created: " + "\'" + !st.isClosed() + "'");
				ResultSet rs = st
						.executeQuery("SELECT bezeichnung2,bezeichnung1,geburtsdatum FROM kontakt WHERE patientnr ='"
								+ trimmedId + "'");
				while (rs.next()) {
					// Setting automatic.patient.assignment mode on/off
					if (properties.getProperty("automatic.patient.assignment").equalsIgnoreCase("true")) {
						name = rs.getString(2) + ", " + rs.getString(1);
					} else {
						name = rs.getString(1) + ", " + rs.getString(2);
					}
					birthdate = rs.getString(3);
					setResolved(!rs.getString(1).isEmpty() && !rs.getString(2).isEmpty() && !rs.getString(3).isEmpty());

					logger.info("Patient ID " + id + " is resolved: " + resolved);
					logger.info("Automatic Patient Assignment Mode: " + "\'"
						+ properties.getProperty("automatic.patient.assignment") + "'");
					logger.info("Identified Name: " + "\'" + name + "'");
					logger.info("Identified Birthdate: " + "\'" + birthdate + "'");
				}
				if (!isResolved()) {
					logger.info("Message/Patient with ID (" + getId() + ") could not be resolved");
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
			logger.info("No numeric ID (" + getId() + "). Message will be skipped");
		}
	}

	private String trim(String id) {
		StringBuilder trimmed = new StringBuilder();
		int i = 0;
		for (char c : id.toCharArray()) {
			if (c!='0') {
				break;
			}
			i++;
		}
		
		String result = id.substring(i, id.length());
		System.out.println(result);
		
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

