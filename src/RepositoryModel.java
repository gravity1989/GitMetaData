import java.io.Serializable;
import java.util.List;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;


public class RepositoryModel  implements Serializable{
	
	Repository repository;
	List<Contributor> contributorList;
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public List<Contributor> getContributorList() {
		return contributorList;
	}
	public void setContributorList(List<Contributor> contributorList) {
		this.contributorList = contributorList;
	}

}
