import java.util.List;

import com.jpoweredcart.common.entity.catalog.InformationToStore;
import org.springframework.util.AutoPopulatingList;


public class TryAutoPopulatingList {

	public static void main(String[] args) {
		List<InformationToStore> l = new AutoPopulatingList<InformationToStore>(InformationToStore.class);
		System.out.println(l.size());
		l.get(0).setStoreId(1);
		System.out.println(l.size());
		
	}
}
