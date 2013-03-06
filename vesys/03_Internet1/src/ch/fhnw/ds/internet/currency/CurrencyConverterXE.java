package ch.fhnw.ds.internet.currency;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

// uses the currency converter provided at http://www.xe.com/currencyconverter/
public class CurrencyConverterXE {
	
	public static void main(String[] args) {

		final JTextField amount = new JTextField(10);
		final JTextField result = new JTextField(10);
		result.setEditable(false);
		final JComboBox<String> from = new JComboBox<>(currencies.keySet().toArray(new String[]{}));
		final JComboBox<String> to   = new JComboBox<>(currencies.keySet().toArray(new String[]{}));
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				result.setText("computing...");
				new Thread(){
					@Override
					public void run(){
						result.setText(computeResult(
								amount.getText(),
								currencies.get(from.getSelectedItem()),
								currencies.get(to.getSelectedItem()))
							);
					}
				}.start();
			}
		});
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		JPanel p = new JPanel(new GridLayout(2,2));
		
		p.add(from);
		p.add(to);	
		p.add(amount);
		p.add(result);
		
		f.add("Center", p);
		f.add("South",  submit);
		
		f.pack();
		f.setVisible(true);
	}

	static String computeResult(String amount, String from, String to){
		String TOKEN = "<td width=\"47%\" align=\"left\">";
		try {
			String query = "http://www.xe.com/currencyconverter/convert/?Amount="+amount+"&From="+from+"&To="+to;
			System.out.println(query);
			URL url = new URL(query);
			URLConnection c = url.openConnection();
			c.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
			BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String line = r.readLine();
			while(line != null){
				int pos = line.indexOf(TOKEN, 0);
				if(pos != -1){
					pos = line.indexOf(">", pos);
					String res = line.substring(pos+1);
					return res.substring(0, res.indexOf("&"));
				}
				line = r.readLine();
			}
			return "no result found";
		} catch (Exception e) {
			String msg = e.getMessage();
			if("".equals(msg)) return e.toString(); else return msg;
		}
	}
	static Map<String,String> currencies = new TreeMap<String,String>();
	static {
		currencies.put("AFN - Afghan Afghani", "AFN");
		currencies.put("ALL - Albanian Lek", "ALL");
		currencies.put("DZD - Algerian Dinar", "DZD");
		currencies.put("AOA - Angolan Kwanza", "AOA");
		currencies.put("ARS - Argentine Peso", "ARS");
		currencies.put("AMD - Armenian Dram", "AMD");
		currencies.put("AWG - Aruban or Dutch Guilder", "AWG");
		currencies.put("AUD - Australian Dollar", "AUD");
		currencies.put("AZN - Azerbaijani New Manat", "AZN");
		currencies.put("BSD - Bahamian Dollar", "BSD");
		currencies.put("BHD - Bahraini Dinar", "BHD");
		currencies.put("BDT - Bangladeshi Taka", "BDT");
		currencies.put("BBD - Barbadian or Bajan Dollar", "BBD");
		currencies.put("BYR - Belarusian Ruble", "BYR");
		currencies.put("BZD - Belizean Dollar", "BZD");
		currencies.put("BMD - Bermudian Dollar", "BMD");
		currencies.put("BTN - Bhutanese Ngultrum", "BTN");
		currencies.put("BOB - Bolivian Boliviano", "BOB");
		currencies.put("BAM - Bosnian Convertible Marka", "BAM");
		currencies.put("BWP - Botswana Pula", "BWP");
		currencies.put("BRL - Brazilian Real", "BRL");
		currencies.put("BND - Bruneian Dollar", "BND");
		currencies.put("BGN - Bulgarian Lev", "BGN");
		currencies.put("BIF - Burundian Franc", "BIF");
		currencies.put("KHR - Cambodian Riel", "KHR");
		currencies.put("CAD - Canadian Dollar", "CAD");
		currencies.put("CVE - Cape Verdean Escudo", "CVE");
		currencies.put("KYD - Caymanian Dollar", "KYD");
		currencies.put("CLP - Chilean Peso", "CLP");
		currencies.put("CNY - Chinese Yuan Renminbi", "CNY");
		currencies.put("COP - Colombian Peso", "COP");
		currencies.put("XOF - CFA Franc", "XOF");
		currencies.put("XAF - Central African CFA Franc BEAC", "XAF");
		currencies.put("KMF - Comoran Franc", "KMF");
		currencies.put("XPF - CFP Franc", "XPF");
		currencies.put("CDF - Congolese Franc", "CDF");
		currencies.put("CRC - Costa Rican Colon", "CRC");
		currencies.put("HRK - Croatian Kuna", "HRK");
		currencies.put("CUC - Cuban Convertible Peso", "CUC");
		currencies.put("CUP - Cuban Peso", "CUP");
		currencies.put("CZK - Czech Koruna", "CZK");
		currencies.put("DKK - Danish Krone", "DKK");
		currencies.put("DJF - Djiboutian Franc", "DJF");
		currencies.put("DOP - Dominican Peso", "DOP");
		currencies.put("XCD - East Caribbean Dollar", "XCD");
		currencies.put("EGP - Egyptian Pound", "EGP");
		currencies.put("SVC - Salvadoran Colon", "SVC");
		currencies.put("ERN - Eritrean Nakfa", "ERN");
		currencies.put("ETB - Ethiopian Birr", "ETB");
		currencies.put("EUR - Euro", "EUR");
		currencies.put("FKP - Falkland Island Pound", "FKP");
		currencies.put("FJD - Fijian Dollar", "FJD");
		currencies.put("GMD - Gambian Dalasi", "GMD");
		currencies.put("GEL - Georgian Lari", "GEL");
		currencies.put("GHS - Ghanaian Cedi", "GHS");
		currencies.put("GIP - Gibraltar Pound", "GIP");
		currencies.put("XAU - Gold Ounce", "XAU");
		currencies.put("GTQ - Guatemalan Quetzal", "GTQ");
		currencies.put("GGP - Guernsey Pound", "GGP");
		currencies.put("GNF - Guinean Franc", "GNF");
		currencies.put("GYD - Guyanese Dollar", "GYD");
		currencies.put("HTG - Haitian Gourde", "HTG");
		currencies.put("HNL - Honduran Lempira", "HNL");
		currencies.put("HKD - Hong Kong Dollar", "HKD");
		currencies.put("HUF - Hungarian Forint", "HUF");
		currencies.put("ISK - Icelandic Krona", "ISK");
		currencies.put("INR - Indian Rupee", "INR");
		currencies.put("IDR - Indonesian Rupiah", "IDR");
		currencies.put("XDR - IMF Special Drawing Rights", "XDR");
		currencies.put("IRR - Iranian Rial", "IRR");
		currencies.put("IQD - Iraqi Dinar", "IQD");
		currencies.put("IMP - Isle of Man Pound", "IMP");
		currencies.put("ILS - Israeli Shekel", "ILS");
		currencies.put("JMD - Jamaican Dollar", "JMD");
		currencies.put("JPY - Japanese Yen", "JPY");
		currencies.put("JEP - Jersey Pound", "JEP");
		currencies.put("JOD - Jordanian Dinar", "JOD");
		currencies.put("KZT - Kazakhstani Tenge", "KZT");
		currencies.put("KES - Kenyan Shilling", "KES");
		currencies.put("KPW - North Korean Won", "KPW");
		currencies.put("KRW - South Korean Won", "KRW");
		currencies.put("KWD - Kuwaiti Dinar", "KWD");
		currencies.put("KGS - Kyrgyzstani Som", "KGS");
		currencies.put("LAK - Lao or Laotian Kip", "LAK");
		currencies.put("LVL - Latvian Lat", "LVL");
		currencies.put("LBP - Lebanese Pound", "LBP");
		currencies.put("LSL - Basotho Loti", "LSL");
		currencies.put("LRD - Liberian Dollar", "LRD");
		currencies.put("LYD - Libyan Dinar", "LYD");
		currencies.put("LTL - Lithuanian Litas", "LTL");
		currencies.put("MOP - Macau Pataca", "MOP");
		currencies.put("MKD - Macedonian Denar", "MKD");
		currencies.put("MGA - Malagasy Ariary", "MGA");
		currencies.put("MWK - Malawian Kwacha", "MWK");
		currencies.put("MYR - Malaysian Ringgit", "MYR");
		currencies.put("MVR - Maldivian Rufiyaa", "MVR");
		currencies.put("MRO - Mauritanian Ouguiya", "MRO");
		currencies.put("MUR - Mauritian Rupee", "MUR");
		currencies.put("MXN - Mexican Peso", "MXN");
		currencies.put("MDL - Moldovan Leu", "MDL");
		currencies.put("MNT - Mongolian Tughrik", "MNT");
		currencies.put("MAD - Moroccan Dirham", "MAD");
		currencies.put("MZN - Mozambican Metical", "MZN");
		currencies.put("MMK - Burmese Kyat", "MMK");
		currencies.put("NAD - Namibian Dollar", "NAD");
		currencies.put("NPR - Nepalese Rupee", "NPR");
		currencies.put("ANG - Dutch Guilder", "ANG");
		currencies.put("NZD - New Zealand Dollar", "NZD");
		currencies.put("NIO - Nicaraguan Cordoba", "NIO");
		currencies.put("NGN - Nigerian Naira", "NGN");
		currencies.put("NOK - Norwegian Krone", "NOK");
		currencies.put("OMR - Omani Rial", "OMR");
		currencies.put("PKR - Pakistani Rupee", "PKR");
		currencies.put("XPD - Palladium Ounce", "XPD");
		currencies.put("PAB - Panamanian Balboa", "PAB");
		currencies.put("PGK - Papua New Guinean Kina", "PGK");
		currencies.put("PYG - Paraguayan Guarani", "PYG");
		currencies.put("PEN - Peruvian Nuevo Sol", "PEN");
		currencies.put("PHP - Philippine Peso", "PHP");
		currencies.put("XPT - Platinum Ounce", "XPT");
		currencies.put("PLN - Polish Zloty", "PLN");
		currencies.put("QAR - Qatari Riyal", "QAR");
		currencies.put("RON - Romanian New Leu", "RON");
		currencies.put("RUB - Russian Ruble", "RUB");
		currencies.put("RWF - Rwandan Franc", "RWF");
		currencies.put("SHP - Saint Helenian Pound", "SHP");
		currencies.put("WST - Samoan Tala", "WST");
		currencies.put("STD - Sao Tomean Dobra", "STD");
		currencies.put("SAR - Saudi Arabian Riyal", "SAR");
		currencies.put("SPL - Seborgan Luigino", "SPL");
		currencies.put("RSD - Serbian Dinar", "RSD");
		currencies.put("SCR - Seychellois Rupee", "SCR");
		currencies.put("SLL - Sierra Leonean Leone", "SLL");
		currencies.put("XAG - Silver Ounce", "XAG");
		currencies.put("SGD - Singapore Dollar", "SGD");
		currencies.put("SBD - Solomon Islander Dollar", "SBD");
		currencies.put("SOS - Somali Shilling", "SOS");
		currencies.put("ZAR - South African Rand", "ZAR");
		currencies.put("LKR - Sri Lankan Rupee", "LKR");
		currencies.put("SDG - Sudanese Pound", "SDG");
		currencies.put("SRD - Surinamese Dollar", "SRD");
		currencies.put("SZL - Swazi Lilangeni", "SZL");
		currencies.put("SEK - Swedish Krona", "SEK");
		currencies.put("CHF - Swiss Franc", "CHF");
		currencies.put("SYP - Syrian Pound", "SYP");
		currencies.put("TWD - Taiwan New Dollar", "TWD");
		currencies.put("TJS - Tajikistani Somoni", "TJS");
		currencies.put("TZS - Tanzanian Shilling", "TZS");
		currencies.put("THB - Thai Baht", "THB");
		currencies.put("TOP - Tongan Pa&#039;anga", "TOP");
		currencies.put("TTD - Trinidadian Dollar", "TTD");
		currencies.put("TND - Tunisian Dinar", "TND");
		currencies.put("TRY - Turkish Lira", "TRY");
		currencies.put("TMT - Turkmenistani Manat", "TMT");
		currencies.put("TVD - Tuvaluan Dollar", "TVD");
		currencies.put("UGX - Ugandan Shilling", "UGX");
		currencies.put("UAH - Ukrainian Hryvna", "UAH");
		currencies.put("AED - Emirati Dirham", "AED");
		currencies.put("GBP - British Pound", "GBP");
		currencies.put("USD - US Dollar", "USD");
		currencies.put("UYU - Uruguayan Peso", "UYU");
		currencies.put("UZS - Uzbekistani Som", "UZS");
		currencies.put("VUV - Ni-Vanuatu Vatu", "VUV");
		currencies.put("VEF - Venezuelan Bolivar", "VEF");
		currencies.put("VND - Vietnamese Dong", "VND");
		currencies.put("YER - Yemeni Rial", "YER");
		currencies.put("ZMK - Zambian Kwacha", "ZMK");
		currencies.put("ZMW - Zambian Kwacha", "ZMW");
		currencies.put("ZWD - Zimbabwean Dollar", "ZWD");
	}
}
