package expense.track.application.repository;
import expense.track.application.entity.WalletDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WalletDescriptionRepository extends JpaRepository<WalletDescription,String> {

   @Query("from WalletDescription where type=:type")
   public WalletDescription getWalletDescription(@Param("type") String type);

   @Query("select creditAmount from WalletDescription where type=:type")
   public int getCredits(@Param("type") String type);

   @Query("select minimumBalance from WalletDescription where walletDescriptionId=:walletDescriptionId")
   public int getMinimumBalance(@Param("walletDescriptionId") String id);
}
