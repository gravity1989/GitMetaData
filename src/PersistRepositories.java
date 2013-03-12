import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;


public class PersistRepositories {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		for (int i = 0; i < 2; i++) {
			System.out.println("/home/themonkey/githubData/repoData"+i+".out");
			File inputFile=new File("/home/themonkey/githubData/repoData"+i+".out");
			ArrayList<RepositoryModel> repoCollection=(ArrayList<RepositoryModel>) deSerialize(inputFile);
			Iterator<RepositoryModel> repoIterator=repoCollection.iterator();
			System.out.println(repoCollection);
			while(repoIterator.hasNext()){
				RepositoryModel repositoryModel=repoIterator.next();
				Repository repository=repositoryModel.getRepository();
				System.out.println("*************************"+repository.getName());
				java.util.List<Contributor> contributorList=repositoryModel.getContributorList();
				Iterator<Contributor> contributorIterator=contributorList.iterator();
				while(contributorIterator.hasNext()){
					Contributor contributor=contributorIterator.next();
					System.out.println(contributor.getName()+"\t"+ contributor.getLogin());
				}
				/*RepositoryService repoService=new RepositoryService();
				java.util.List<Contributor> contributorList=repoService.getContributors(repository, true);
				Iterator<Contributor> contributorIterator=contributorList.iterator();
				while(contributorIterator.hasNext()){
					Contributor contributor=contributorIterator.next();
					System.out.println(contributor.getName()+"\t"+ contributor.getLogin());
				}*/
				
			}
		}
		
		
		
	}
	
	public static Object deSerialize(File inputFile) throws IOException, ClassNotFoundException{
		
		FileInputStream fis=new FileInputStream(inputFile);
		ObjectInputStream ois=new ObjectInputStream(fis);
		System.out.println("deserialize");
		return ois.readObject();
	}

}
