package cz.zcu.luk.mwes.mains;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 10.2.14
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
 */
public class MwePairScoredGenerator {
    public static void main(String args[]){
        for (int i = 24; i < 27; i++) {
            System.out.print("INSERT INTO `mwe_data`.`mwe_pair_scored` ( `id` ,`id_user` ,`id_mwe_pair` ,`score` ,`last_update_date` ,`last_update_time` ) VALUES " );
            for (int j = 0; j < 1000; j++) {
                System.out.print("(NULL , '"+(i+1)+"', '"+ (j+1) +"', '', '', ''), ");
            }
            System.out.println();
        }

    }
}
