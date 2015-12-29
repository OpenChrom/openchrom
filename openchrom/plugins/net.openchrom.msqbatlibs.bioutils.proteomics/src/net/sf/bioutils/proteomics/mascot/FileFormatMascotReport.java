/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.mascot;

import java.util.ArrayList;
import java.util.List;

public class FileFormatMascotReport {

	public final static String PROT_HIT_NUM = "prot_hit_num";
	public final static String PROT_ACC = "prot_acc";
	public final static String PROT_DESC = "prot_desc";
	public final static String PROT_SCORE = "prot_score";
	public final static String PROT_MASS = "prot_mass";
	public final static String F_NA = "prot_matches";
	public final static String G_NA = "prot_matches_sig";
	public final static String H_NA = "prot_sequences";
	public final static String I_NA = "prot_sequences_sig";
	public final static String J_NA = "prot_cover";
	public final static String K_NA = "prot_len";
	public final static String L_NA = "prot_pi";
	public final static String M_NA = "prot_tax_str";
	public final static String N_NA = "prot_tax_id";
	public final static String O_NA = "prot_seq";
	public final static String P_NA = "pep_query";
	public final static String Q_NA = "pep_rank";
	public final static String R_NA = "pep_isbold";
	public final static String S_NA = "pep_isunique";
	public final static String PEP_EXP_MZ = "pep_exp_mz";
	public final static String PEP_EXP_MR = "pep_exp_mr";
	public final static String U_NA = "pep_exp_z";
	public final static String PEP_CALC_MR = "pep_calc_mr";
	public final static String W_NA = "pep_delta";
	public final static String X_NA = "pep_miss";
	public final static String Y_NA = "pep_score";
	public final static String Z_NA = "pep_expect";
	public final static String ZA_NA = "pep_res_before";
	public final static String ZB_NA = "pep_seq";
	public final static String ZC_NA = "pep_res_after";
	public final static String ZD_NA = "pep_var_mod";
	public final static String ZE_NA = "pep_var_mod_pos";
	public final static String ZF_NA = "pep_summed_mod_pos";
	public final static String PEP_SCAN_TITLE = "pep_scan_title";
	private static List<String> ids = new ArrayList<String>();

	static {
		ids.add(PROT_HIT_NUM);
		ids.add(PROT_ACC);
		ids.add(PROT_DESC);
		ids.add(PROT_SCORE);
		ids.add(PROT_MASS);
		ids.add(F_NA);
		ids.add(G_NA);
		ids.add(H_NA);
		ids.add(I_NA);
		ids.add(J_NA);
		ids.add(K_NA);
		ids.add(L_NA);
		ids.add(M_NA);
		ids.add(N_NA);
		ids.add(O_NA);
		ids.add(P_NA);
		ids.add(Q_NA);
		ids.add(R_NA);
		ids.add(S_NA);
		ids.add(PEP_EXP_MZ);
		ids.add(PEP_EXP_MR);
		ids.add(U_NA);
		ids.add(PEP_CALC_MR);
		ids.add(W_NA);
		ids.add(X_NA);
		ids.add(Y_NA);
		ids.add(Z_NA);
		ids.add(ZA_NA);
		ids.add(ZB_NA);
		ids.add(ZC_NA);
		ids.add(ZD_NA);
		ids.add(ZE_NA);
		ids.add(ZF_NA);
		ids.add(PEP_SCAN_TITLE);
		// ids.add(ZH_NA);
		// ids.add(ZI_NA);
		// ids.add(ZJ_NA);
		// ids.add(ZK_NA);
		// ids.add(ZL_NA);
	}

	public static List<String> getHeaders() {

		return ids;
	}
}
