package cz.zcu.luk.mwes.acl2011;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class NounsToNoun {

	public Map<String, String> getNounsToNounMapping(String nounsMappingFileName) {
		Map<String, String> nounsToNoun = new HashMap<String, String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nounsMappingFileName), "UTF-8"));
			String line;
			String[] lineSplitted;
			while ((line = br.readLine()) != null) {
				lineSplitted = line.split(" ");
				if (lineSplitted[0].equals(lineSplitted[1])) {
					continue;
				}
				else {
					nounsToNoun.put(lineSplitted[0], lineSplitted[1]);
				}
			}
			br.close();
			System.out.println("Nouns to noun mapping loaded..");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nounsToNoun;
	}

}
